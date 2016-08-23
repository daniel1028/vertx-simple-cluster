package org.alpha.routes;
import org.alpha.constants.ConfigConstants;
import org.alpha.constants.MessagebusEndpoints;
import org.alpha.constants.RouteConstants;
import org.alpha.utils.RouteRequestUtility;
import org.alpha.utils.RouteResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

class RouteDemoConfigurator implements RouteConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteDemoConfigurator.class);

    @Override
    public void configureRoutes(Vertx vertx, Router router, JsonObject config) {

        final EventBus eb = vertx.eventBus();

        final long mbusTimeout = config.getLong(ConfigConstants.MBUS_TIMEOUT, 30L);

        router.post(RouteConstants.DEMO_API).handler(routingContext -> {
            String name = routingContext.request().getParam(RouteConstants.DEMO_NAME);
            DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout * 1000);
            eb.send(MessagebusEndpoints.MBEP_DEMO_FIRST, new RouteRequestUtility().getBodyForMessage(routingContext),
                options, reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOGGER));
        });

    }
}
