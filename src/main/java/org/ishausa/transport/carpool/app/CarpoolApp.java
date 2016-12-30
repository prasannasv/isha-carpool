package org.ishausa.transport.carpool.app;

import com.google.common.collect.ImmutableMap;
import org.ishausa.transport.carpool.renderer.SoyRenderer;
import org.ishausa.transport.carpool.security.AuthenticationHandler;
import org.ishausa.transport.carpool.security.HttpsEnforcer;
import spark.utils.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Created by tosri on 12/29/2016.
 */
public class CarpoolApp {
    private static final Logger log = Logger.getLogger(CarpoolApp.class.getName());

    public static void main(final String[] args) {
        exception(Exception.class, (exception, request, response) -> {
            log.log(Level.WARNING, "Exception occurred when serving request to " + request.pathInfo(), exception);
        });

        final String port = System.getenv("PORT");
        if (!StringUtils.isBlank(port)) {
            port(Integer.parseInt(port));
        }

        staticFiles.location(Paths.STATIC);

        before(new HttpsEnforcer());
        before(AuthenticationHandler::ensureAuthenticated);

        get(Paths.LOGIN, (req, res) -> SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.LOGIN));
        post(Paths.VALIDATE_ID_TOKEN, AuthenticationHandler::finishLogin);

        get(Paths.INDEX, (req, res) -> SoyRenderer.INSTANCE.render(SoyRenderer.CarPoolAppTemplate.INDEX,
                ImmutableMap.of("name", req.session().attribute(AuthenticationHandler.NAME))));
    }
}
