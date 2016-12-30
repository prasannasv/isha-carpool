package org.ishausa.transport.carpool.app;

import static spark.Spark.*;

/**
 * Created by tosri on 12/29/2016.
 */
public class CarpoolApp {
    public static void main(final String[] args) {
        port(Integer.parseInt(System.getenv("PORT")));

        get("/", (req, res) -> "Hello World!");
    }
}
