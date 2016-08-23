package org.alpha.bootstrap;

import org.alpha.constants.MessagebusEndpoints;
import org.alpha.responses.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class DemoVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(DemoVerticle.class);

  @Override
  public void start(Future<Void> voidFuture) throws Exception {

    EventBus eb = vertx.eventBus();

    vertx.executeBlocking(blockingFuture -> {
      startApplication();
      blockingFuture.complete();
    }, startApplicationFuture -> {
      if (startApplicationFuture.succeeded()) {
        eb.consumer(MessagebusEndpoints.MBEP_DEMO_FIRST, message -> {
          LOGGER.debug("Received message: '{}'", message.body());
          vertx.executeBlocking(future -> {
            MessageResponse result = new MessageResponse.Builder().successful().setStatusOkay().setResponseBody(new JsonObject(message.body().toString())).build();
            future.complete(result);
          }, res -> {
            MessageResponse result = (MessageResponse) res.result();
            LOGGER.debug("Sending response: '{}'", result.reply());
            message.reply(result.reply(), result.deliveryOptions());
          });
        }).completionHandler(result -> {
          if (result.succeeded()) {
            LOGGER.info("Demo end point ready to listen");
            voidFuture.complete();
          } else {
            LOGGER.error("Error registering demo handler. Halting the Class machinery");
            voidFuture.fail(result.cause());
            Runtime.getRuntime().halt(1);
          }
        });
      } else {
        voidFuture.fail("Not able to initialize the Demo machinery properly");
      }
    });

  }

  @Override
  public void stop() throws Exception {
    shutDownApplication();
    super.stop();
  }

  private void startApplication() {
    LOGGER.info("Nothing to start....");
  }

  private void shutDownApplication() {
    LOGGER.info("Nothing to shutdown....");
  }
}
