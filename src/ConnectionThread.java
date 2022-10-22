import java.net.Socket;
import java.io.*;

public class ConnectionThread extends Thread {
  private Socket socket;

  public ConnectionThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    while (this.socket.isConnected()) {
      try {
        InputStream input = this.socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String command = reader.readLine();

        OutputStream output = this.socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        if (command == null) { // i have to do 2 ifs instead of using || cuz equals throws exception if null
          socket.close();
          return;
        }
        if (command.equals("EXIT")) {
          socket.close();
          return;
        }
        writer.println(Commands.handle(command));
      } catch (IOException e) {
        System.out.println("Handling client connection failed (IOException) - " + e.getMessage());
      }
    }
  }
}
