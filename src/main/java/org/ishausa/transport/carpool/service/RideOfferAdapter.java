package org.ishausa.transport.carpool.service;

import lombok.Data;
import org.ishausa.transport.carpool.model.OfferRequestMatch;
import org.ishausa.transport.carpool.model.User;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Converts the RideOffer instance to an object that is view friendly.
 *
 * Created by Prasanna Venkat on 1/2/2017.
 */
public class RideOfferAdapter {
    private static final Logger log = Logger.getLogger(RideOfferAdapter.class.getName());

    private final UsersService usersService;
    private final OfferRequestMatchesService offerRequestMatchesService;

    public RideOfferAdapter(final UsersService usersService,
                            final OfferRequestMatchesService offerRequestMatchesService) {
        this.usersService = usersService;
        this.offerRequestMatchesService = offerRequestMatchesService;
    }

    public RideOffer transform(final org.ishausa.transport.carpool.model.RideOffer rideOffer) {
        if (rideOffer == null) {
            return null;
        }
        log.info("Transforming rideOffer: " + rideOffer);

        final User user = usersService.findById(rideOffer.getUserId());
        log.info("Found user for userId: " + rideOffer.getUserId() + ", user: " + user);

        final RideOffer adaptedOffer = new RideOffer();

        adaptedOffer.setOfferedBy(String.format("%s (%s)", user.getName(), user.getEmail()));
        adaptedOffer.setOfferedOn(rideOffer.getOfferedOn());
        adaptedOffer.setSeatsOffered(rideOffer.getSeatsOffered());

        final List<OfferRequestMatch> matchedRequests =
                offerRequestMatchesService.findMatchedOffersForTripAndOffer(rideOffer.getTripId(), rideOffer.getId());
        log.info("Found matched requests as: " + matchedRequests);

        final int seatsMatched = matchedRequests.stream().mapToInt(OfferRequestMatch::getSeatsMatched).sum();
        log.info("Seats matched: " + seatsMatched);

        adaptedOffer.setSeatsMatched(seatsMatched);

        return adaptedOffer;
    }

    @Data
    public static class RideOffer {
        String offeredBy;
        int seatsOffered;
        Date offeredOn;
        int seatsMatched;
    }
}
