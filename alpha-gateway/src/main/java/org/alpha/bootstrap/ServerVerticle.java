package org.alpha.bootstrap;

import org.alpha.constants.ConfigConstants;
import org.alpha.routes.RouteConfiguration;
import org.alpha.routes.RouteConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

public class ServerVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServerVerticle.class);

  @Override
  public void start(Future<Void> voidFuture) throws Exception {

    LOGGER.info("Starting ServerVerticle...");
    final HttpServer httpServer = vertx.createHttpServer();

    final Router router = Router.router(vertx);

    // Register the routes
    RouteConfiguration rc = new RouteConfiguration();
    for (RouteConfigurator configurator : rc) {
      configurator.configureRoutes(vertx, router, config());
    }

    // If the port is not present in configuration then we end up
    // throwing as we are casting it to int. This is what we want.
    final int port = config().getInteger(ConfigConstants.HTTP_PORT);
    LOGGER.info("Http server starting on port {}", port);
    httpServer.requestHandler(router::accept).listen(port, result -> {
      if (result.succeeded()) {
        LOGGER.info("HTTP Server started successfully");
        voidFuture.complete();
      } else {
        // Can't do much here, Need to Abort. However, trying to exit
        // may have us blocked on other threads that we may have
        // spawned, so we need to use
        // brute force here
        LOGGER.error("Not able to start HTTP Server", result.cause());
        voidFuture.fail(result.cause());
        Runtime.getRuntime().halt(1);
      }
    });

  }

}
