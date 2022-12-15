package coffeedb;

import java.net.Socket;
import java.io.*;
import ziph.*;

public class ConnectionThread extends Thread {
  private final Socket socket;
  private final String password;
  private final String dbFilePath;

  public ConnectionThread(Socket socket, String password, String dbFilePath) {
    this.socket = socket;
    this.password = password;
    this.dbFilePath = dbFilePath;
  }

  public void run() {
    boolean authenticated = false;
    while (this.socket.isConnected()) {
      try {
        String requestAsJsonString = new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine();
        PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
        if (requestAsJsonString == null)
          return;
        try {
          JSONObject request = new JSONObjectFromString(requestAsJsonString);
          JSONObject response = new JSONObject();
          if (!authenticated)
            if (request.get("action") == null) {
              response.set("status", "error");
              response.set("message", "Action is missing");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (!(request.get("action") instanceof String)) {
              response.set("status", "error");
              response.set("message", "Action must be a string");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (!request.get("action").equals("authenticate")) {
              response.set("status", "error");
              response.set("message", "Please authenticate first");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (request.get("password") == null) {
              response.set("status", "error");
              response.set("message", "Password is missing");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (!(request.get("password") instanceof String)) {
              response.set("status", "error");
              response.set("message", "Password must be a string");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (request.get("password").equals(this.password)) {
              authenticated = true;
              response.set("status", "success");
              response.set("message", "Authentication complete");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else {
              response.set("status", "error");
              response.set("message", "Incorrect password");
              response.setNull("payload");
              writer.println(response.toJSONString());
            }
          else if (request.get("action") == null) {
            response.set("status", "error");
            response.set("message", "Action is missing");
            response.setNull("payload");
            writer.println(response.toJSONString());
          } else if (!(request.get("action") instanceof String)) {
            response.set("status", "error");
            response.set("message", "Action must be a string");
            response.setNull("payload");
            writer.println(response.toJSONString());
          } else if (!request.get("action").equals("authenticate")) {
            response.set("status", "error");
            response.set("message", "Already authenticated");
            response.setNull("payload");
            writer.println(response.toJSONString());
          } else {
            response = (new RequestHandler(request)).getResponse();
            writer.println(response.toJSONString());
          }
        } catch (InvalidJSONException e) {
          JSONObject response = new JSONObject();
          response.set("status", "error");
          response.set("message", "Invalid request body");
          response.setNull("payload");
          writer.println(response.toJSONString());
        }
      } catch (IOException e) {
        System.out.println("Handling client connection failed (IOException) - " + e.getMessage());
        return;
      }
    }
  }
}
