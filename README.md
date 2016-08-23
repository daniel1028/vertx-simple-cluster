# vertx-simple-cluster

Building gateway and handler
==============

## Prerequisites

- Gradle 2.7
- Java 8

## Running Build

The default task is *shadowJar* which is provided by plugin. So running *gradle* from command line will run *shadowJar* and it will create a fat jar in build/libs folder. Note that there is artifact name specified in build file and hence it will take the name from directory housing the project.

Once the far Jar is created, it could be run as any other Java application.

## Running the Jar as an application

Following command could be used, from the base directory.

Note that any options that need to be passed onto Vertx instance need to be passed at command line e.g, worker pool size etc

>  java -classpath build/libs/alpha-gateway-0.1-snapshot-fat.jar: -Dvertx.metrics.options.enabled=true -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory io.vertx.core.Launcher -conf src/main/resources/alpha-server.json -cluster -instances 4

>  java -classpath build/libs/alpha-demo-handler-0.1-snapshot-fat.jar: -Dvertx.metrics.options.enabled=true -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory io.vertx.core.Launcher -cluster -instances 4

========================================
# Example API to test this application 

Type : POST

End Point : http://localhost:8080/api/alpha/v1/demo/nothing

Request Body: {"key":"value"}
