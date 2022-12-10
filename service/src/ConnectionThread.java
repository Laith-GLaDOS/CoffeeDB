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
          writer.println(request.toJSONString());
        } catch (InvalidJSONException e) {
          JSONObject response = new JSONObject();
          response.set("status", "error");
          response.set("message", "Invalid request body, server was expecting JSON");
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
