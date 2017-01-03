package org.ishausa.transport.carpool.service;

import org.ishausa.transport.carpool.model.OfferRequestMatch;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by tosri on 1/2/2017.
 */
public class OfferRequestMatchService {
    private final Datastore datastore;

    public OfferRequestMatchService(final Datastore datastore) {
        this.datastore = datastore;
    }

    public List<OfferRequestMatch> findMatchedOffersForTripAndOffer(final String tripId, final String offerId) {
        return datastore.createQuery(OfferRequestMatch.class)
                .filter("tripId=", tripId)
                .filter("offerId=", offerId)
                .asList();
    }
}
