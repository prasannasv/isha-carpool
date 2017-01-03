package org.ishausa.transport.carpool.app;

/**
 * Created by Prasanna Venkat on 12/30/2016.
 */
public class Paths {
    public static final String STATIC = "/static";
    public static final String MAIN = "/";
    public static final String LOGIN = "/login";
    public static final String VALIDATE_ID_TOKEN = "/validate_id_token";

    private static final String API_VERSION = "/api/v1";

    public static final String TRIPS = API_VERSION + "/trips";
    public static final String ID_PARAM = ":id";
    public static final String TRIP_BY_ID = TRIPS + "/" + ID_PARAM;

    public static final String RIDE_OFFER_FOR_TRIP_ID = TRIP_BY_ID + "/ride_offer";
    public static final String RIDE_REQUEST_FOR_TRIP_ID = TRIP_BY_ID + "/ride_request";
    public static final String RIDE_OFFERS_FOR_TRIP_ID = TRIP_BY_ID + "/ride_offers";
}
