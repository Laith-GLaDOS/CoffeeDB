package coffeedb;

import ziph.*;

public class RequestHandler {
  private final JSONObject request;
  private JSONObject response;

  public JSONObject getResponse() {
    return this.response;
  }

  private void handleRequest() {
    this.response = new JSONObject();
  }

  public RequestHandler(JSONObject request) {
    this.request = request;
    this.handleRequest();
  }
}
