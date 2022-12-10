import java.net.Socket;
import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import ziph.*;

public class ConnectionThread extends Thread {
  private final Socket socket;

  public ConnectionThread(Socket socket) {
    this.socket = socket;
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
          if (!authenticated)
            if (request.get("action") == null) {
              JSONObject response = new JSONObject();
              response.set("status", "error");
              response.set("message", "Action is missing");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (!request.get("action").equals("authenticate")) {
              JSONObject response = new JSONObject();
              response.set("status", "error");
              response.set("message", "Please authenticate first");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else if (request.get("password") == null) {
              JSONObject response = new JSONObject();
              response.set("status", "error");
              response.set("message", "Password is missing");
              response.setNull("payload");
              writer.println(response.toJSONString());
            } else {
              File passwordFile = new File("./coffeedb_password");
              Scanner passwordFileReader = new Scanner(passwordFile);
              if (passwordFileReader.hasNextLine())
                if (request.get("password").equals(passwordFileReader.nextLine())) {
                  authenticated = true;
                  JSONObject response = new JSONObject();
                  response.set("status", "success");
                  response.set("message", "Authentication complete");
                  response.setNull("payload");
                  writer.println(response.toJSONString());
                } else {
                  JSONObject response = new JSONObject();
                  response.set("status", "error");
                  response.set("message", "Incorrect password");
                  response.setNull("payload");
                  writer.println(response.toJSONString());
                }
              passwordFileReader.close();
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
