package org.ishausa.transport.carpool.app;

import spark.utils.StringUtils;

import static spark.Spark.*;

/**
 * Created by tosri on 12/29/2016.
 */
public class CarpoolApp {
    public static void main(final String[] args) {
        final String port = System.getenv("PORT");
        if (!StringUtils.isBlank(port)) {
            port(Integer.parseInt(port));
        }

        get("/", (req, res) -> "Hello World!");
    }
}
