package org.ishausa.transport.carpool.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.ishausa.transport.carpool.model.MatchType;
import org.ishausa.transport.carpool.model.RideOffer;
import org.ishausa.transport.carpool.model.User;
import org.mongodb.morphia.Datastore;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Prasanna Venkat on 1/2/2017.
 */
public class RideOffersService {
    private static final Logger log = Logger.getLogger(RideOffersService.class.getName());

    private static final Gson GSON = new GsonBuilder().create();

    private final Datastore datastore;
    private final RideOfferAdapter rideOfferAdapter;

    public RideOffersService(final Datastore datastore,
                             final UsersService usersService,
                             final OfferRequestMatchesService offerRequestMatchesService) {
        this.datastore = datastore;
        this.rideOfferAdapter = new RideOfferAdapter(usersService, offerRequestMatchesService);
    }

    public void createRideOffer(final User offerer, final String tripId, final String jsonBody) {
        final JsonObject rideOfferRequest = GSON.fromJson(jsonBody, JsonElement.class).getAsJsonObject();
        final int seatsOffered = rideOfferRequest.get("seats").getAsInt();

        final RideOffer rideOffer =
                new RideOffer(tripId, offerer.getUserId(), seatsOffered, MatchType.AUTOMATIC, new Date());

        datastore.save(rideOffer);

        log.info("Created ride offer from jsonBody: " + jsonBody + " for user: " + offerer + ", as: " + rideOffer);
    }

    public List<RideOfferAdapter.RideOffer> findForTrip(final String tripId) {
        return datastore.createQuery(RideOffer.class)
                .filter("tripId =", tripId)
                .asList()
                .stream()
                .map(rideOfferAdapter::transform)
                .collect(Collectors.toList());
    }

    public RideOfferAdapter.RideOffer findForTripAndUser(final String tripId, final String userId) {
        final RideOffer rideOffer = datastore.createQuery(RideOffer.class)
                .filter("tripId =", tripId)
                .filter("userId =", userId)
                .get();
        log.info("RideOffer fetched for tripId: " + tripId + " and userId:" + userId + ", is: " + rideOffer);

        return rideOfferAdapter.transform(rideOffer);
    }
}
