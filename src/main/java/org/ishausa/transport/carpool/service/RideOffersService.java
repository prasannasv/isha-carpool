package org.ishausa.transport.carpool.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ishausa.transport.carpool.model.RideOffer;
import org.mongodb.morphia.Datastore;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by tosri on 1/2/2017.
 */
public class RideOffersService {
    private static final Logger log = Logger.getLogger(RideOffersService.class.getName());

    private static final Gson GSON = new GsonBuilder().create();

    private final Datastore datastore;
    private final RideOfferAdapter rideOfferAdapter;

    public RideOffersService(final Datastore datastore,
                             final UsersService usersService,
                             final OfferRequestMatchService offerRequestMatchService) {
        this.datastore = datastore;
        this.rideOfferAdapter = new RideOfferAdapter(usersService, offerRequestMatchService);
    }

    public void createRideOffer(final String jsonBody) {
        final RideOffer rideOffer = GSON.fromJson(jsonBody, RideOffer.class);
        datastore.save(rideOffer);
    }

    public List<RideOfferAdapter.RideOffer> findForTrip(final String tripId) {
        return datastore.createQuery(RideOffer.class)
                .filter("tripId=", tripId)
                .asList()
                .stream()
                .map(rideOfferAdapter::transform)
                .collect(Collectors.toList());
    }

    public RideOfferAdapter.RideOffer findForTripAndUser(final String tripId, final String userId) {
        final RideOffer rideOffer = datastore.createQuery(RideOffer.class)
                .filter("tripId=", tripId)
                .filter("userId=", userId)
                .get();
        log.info("RideOffer fetched for tripId: " + tripId + " and userId:" + userId + ", is: " + rideOffer);

        return rideOfferAdapter.transform(rideOffer);
    }
}
