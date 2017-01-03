package org.ishausa.transport.carpool.app;

import com.google.common.collect.ImmutableMap;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.ishausa.transport.carpool.model.Role;
import org.ishausa.transport.carpool.model.User;
import org.ishausa.transport.carpool.renderer.JsonTransformer;
import org.ishausa.transport.carpool.renderer.SoyRenderer;
import org.ishausa.transport.carpool.security.AuthenticationHandler;
import org.ishausa.transport.carpool.security.HttpsEnforcer;
import org.ishausa.transport.carpool.service.OfferRequestMatchesService;
import org.ishausa.transport.carpool.service.RideOffersService;
import org.ishausa.transport.carpool.service.TripsService;
import org.ishausa.transport.carpool.service.UsersService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import spark.utils.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Created by Prasanna Venkat on 12/29/2016.
 */
public class CarpoolApp {
    private static final Logger log = Logger.getLogger(CarpoolApp.class.getName());

    private final JsonTransformer jsonTransformer;

    private final TripsService tripsService;
    private final UsersService usersService;
    private final RideOffersService rideOffersService;
    private final AuthenticationHandler authenticationHandler;
    private final OfferRequestMatchesService offerRequestMatchesService;

    public static void main(final String[] args) {
        final CarpoolApp app = new CarpoolApp();

        app.init();
    }

    private CarpoolApp() {
        jsonTransformer = new JsonTransformer();
        final Datastore datastore = setupMorphia();
        tripsService = new TripsService(datastore);
        usersService = new UsersService(datastore);
        authenticationHandler = new AuthenticationHandler(usersService);
        offerRequestMatchesService = new OfferRequestMatchesService(datastore);
        rideOffersService = new RideOffersService(datastore, usersService, offerRequestMatchesService);
    }

    private Datastore setupMorphia() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.ishausa.transport.carpool.model");

        final Datastore datastore;
        final String mongoUri = System.getenv("MONGODB_URI");
        if (!StringUtils.isBlank(mongoUri)) {
            final MongoClientURI clientURI = new MongoClientURI(mongoUri);
            datastore = morphia.createDatastore(new MongoClient(clientURI), clientURI.getDatabase());
        } else {
            datastore = morphia.createDatastore(new MongoClient(), "carpool-app");
        }

        datastore.ensureIndexes();

        return datastore;
    }

    private void init() {
        exception(Exception.class, (exception, request, response) -> {
            log.log(Level.WARNING, "Exception occurred when serving request to " + request.pathInfo(), exception);
            throw new RuntimeException(exception);
        });

        final String port = System.getenv("PORT");
        if (!StringUtils.isBlank(port)) {
            port(Integer.parseInt(port));
        }

        staticFiles.location(Paths.STATIC);
        if (RuntimeEnvironment.isOnHeroku()) {
            staticFiles.externalLocation("/app/src/main/webapp");
        } else {
            staticFiles.externalLocation("/Users/tosri/Code/IshaCarPool/src/main/webapp");
        }

        initFilters();

        configureRoutes();
    }

    private void initFilters() {
        before(new HttpsEnforcer());
        before(authenticationHandler::ensureAuthenticated);
    }

    private void configureRoutes() {
        configureAuthEndpoints();

        get(Paths.MAIN, (req, res) ->
                SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.MAIN,
                        ImmutableMap.of("name", req.session().attribute(AuthenticationHandler.NAME))));

        configureTripResourceEndpoints();

        configureRideResourceEndpoints();
    }

    private void configureAuthEndpoints() {
        get(Paths.LOGIN, (req, res) -> SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.LOGIN));
        post(Paths.VALIDATE_ID_TOKEN, authenticationHandler::finishLogin);
    }

    private void configureTripResourceEndpoints() {
        post(Paths.TRIPS, ContentType.JSON, (req, res) -> {
            final User authenticatedUser = req.attribute(AuthenticationHandler.AUTHENTICATED_USER);
            return tripsService.createTrip(authenticatedUser, req.body());
        }, jsonTransformer);

        get(Paths.TRIPS, ContentType.JSON, (req, res) -> tripsService.listAll(), jsonTransformer);

        get(Paths.TRIP_BY_ID, ContentType.JSON,
                (req, res) -> tripsService.find(req.params(Paths.ID_PARAM)), jsonTransformer);
    }

    private void configureRideResourceEndpoints() {
        post(Paths.RIDE_OFFER_FOR_TRIP_ID, ContentType.JSON, (req, res) -> {
            final User authenticatedUser = req.attribute(AuthenticationHandler.AUTHENTICATED_USER);
            final String tripId = req.params(Paths.ID_PARAM);

            rideOffersService.createRideOffer(authenticatedUser, tripId, req.body());

            return res;
        });

        get(Paths.RIDE_OFFERS_FOR_TRIP_ID, ContentType.JSON, (req, res) -> {
            final User user = req.attribute(AuthenticationHandler.AUTHENTICATED_USER);
            final String tripId = req.params(Paths.ID_PARAM);

            return user.getRole() == Role.REGULAR ? rideOffersService.findForTripAndUser(tripId, user.getUserId()) :
                    rideOffersService.findForTrip(tripId);
        }, jsonTransformer);
    }
}
