package org.ishausa.transport.carpool.security;

import spark.Filter;
import spark.Request;
import spark.Response;

/**
 * Created by tosri on 12/30/2016.
 */
public class HttpsEnforcer implements Filter {
    private static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

    @Override
    public void handle(final Request request, final Response response) throws Exception {

        if (!request.url().startsWith("http://localhost:")) {
            if ("http".equals(request.headers(X_FORWARDED_PROTO)) || "http".equals(request.scheme())) {
                response.redirect(request.url().replace("http://", "https://"));
            }
        }
    }
}
