package org.alpha.constants;

public final class RouteConstants {

  private static final String API_VERSION = "v1";
  private static final String API_BASE_ROUTE = "/api/alpha/" + API_VERSION + '/';
  private static final String SEP = "/";
  private static final String COLON = ":";
  public static final String DEMO= "demo";
  public static final String DEMO_NAME = "demoName";

  public static final String DEMO_API = API_BASE_ROUTE + DEMO + SEP + COLON + DEMO_NAME;

  
  private RouteConstants() {
    throw new AssertionError();
  }

}
