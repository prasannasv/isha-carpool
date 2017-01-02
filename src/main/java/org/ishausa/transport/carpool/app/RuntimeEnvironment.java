package org.ishausa.transport.carpool.app;

import spark.utils.StringUtils;

/**
 * Created by tosri on 1/1/2017.
 */
public class RuntimeEnvironment {
    public static boolean isOnHeroku() {
        return StringUtils.isNotBlank(System.getenv("ON_HEROKU"));
    }
}
