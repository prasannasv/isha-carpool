package org.ishausa.transport.carpool.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by tosri on 12/30/2016.
 */
@Data
public class PoolOffer {
    private String id;
    private String tripId;
    private String userId;
    private int seatsOfferred;
    private MatchType matchType;
    private Date offeredOn;
}
