package org.ishausa.transport.carpool.model;

import lombok.Data;

/**
 * Represents a match of a pool request with an offer.
 * For now only complete matches are supported, i.e., a match implies all the seats requested is satisfied by the offer.
 *
 * Created by tosri on 12/30/2016.
 */
@Data
public class OfferRequestMatch {
    private String id;
    private String tripId;
    private String offerId;
    private String requestId;
}
