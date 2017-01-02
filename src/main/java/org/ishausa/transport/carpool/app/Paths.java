package org.ishausa.transport.carpool.app;

/**
 * Created by tosri on 12/30/2016.
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
}
