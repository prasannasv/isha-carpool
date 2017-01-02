package org.ishausa.transport.carpool.app;

import com.google.common.collect.ImmutableMap;
import com.mongodb.MongoClient;
import org.ishausa.transport.carpool.renderer.JsonTransformer;
import org.ishausa.transport.carpool.renderer.SoyRenderer;
import org.ishausa.transport.carpool.security.AuthenticationHandler;
import org.ishausa.transport.carpool.security.HttpsEnforcer;
import org.ishausa.transport.carpool.service.TripsService;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import spark.HaltException;
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
 * Created by tosri on 12/29/2016.
 */
public class CarpoolApp {
    private static final Logger log = Logger.getLogger(CarpoolApp.class.getName());

    private final JsonTransformer jsonTransformer;

    private final TripsService tripsService;

    public static void main(final String[] args) {
        final CarpoolApp app = new CarpoolApp();

        app.init();
    }

    private CarpoolApp() {
        jsonTransformer = new JsonTransformer();
        final Datastore datastore = setupMorphia();
        tripsService = new TripsService(datastore);
    }

    private Datastore setupMorphia() {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("org.ishausa.transport.carpool.model");

        final Datastore datastore = morphia.createDatastore(new MongoClient(), "carpool-app");
        datastore.ensureIndexes();

        return datastore;
    }

    private void init() {
        exception(Exception.class, (exception, request, response) -> {
            log.log(Level.WARNING, "Exception occurred when serving request to " + request.pathInfo(), exception);

            // http://static.javadoc.io/com.sparkjava/spark-core/2.5.4/spark/Spark.html#halt--
            if (exception instanceof HaltException) {
                throw (HaltException) exception;
            }
        });

        final String port = System.getenv("PORT");
        if (!StringUtils.isBlank(port)) {
            port(Integer.parseInt(port));
        }

        staticFiles.location(Paths.STATIC);
        //TODO: detect if it is running on heroku and update the path
        staticFiles.externalLocation("/Users/tosri/Code/IshaCarPool/src/main/webapp");

        initFilters();

        configureRoutes();
    }

    private void initFilters() {
        before(new HttpsEnforcer());
        before(AuthenticationHandler::ensureAuthenticated);
    }

    private void configureRoutes() {
        configureAuthEndpoints();

        get(Paths.MAIN, (req, res) ->
                SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.MAIN,
                        ImmutableMap.of("name", req.session().attribute(AuthenticationHandler.NAME))));

        configureTripResourceEndpoints();
    }

    private void configureAuthEndpoints() {
        get(Paths.LOGIN, (req, res) -> SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.LOGIN));
        post(Paths.VALIDATE_ID_TOKEN, AuthenticationHandler::finishLogin);
    }

    private void configureTripResourceEndpoints() {
        post(Paths.TRIPS, ContentType.JSON, (req, res) -> tripsService.createTrip(req.body()), jsonTransformer);

        get(Paths.TRIPS, ContentType.JSON, (req, res) -> tripsService.listAll(), jsonTransformer);

        get(Paths.TRIP_BY_ID, ContentType.JSON,
                (req, res) -> tripsService.find(req.params(Paths.ID_PARAM)), jsonTransformer);
    }
}
