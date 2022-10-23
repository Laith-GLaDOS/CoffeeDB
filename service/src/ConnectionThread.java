import java.net.Socket;
import java.io.*;
import java.util.Scanner;

public class ConnectionThread extends Thread {
  private Socket socket;

  public ConnectionThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    boolean authenticated = false;

    while (this.socket.isConnected()) {
      try {
        InputStream input = this.socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String command = reader.readLine();

        OutputStream output = this.socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        if (command == null) {
          // i have to do 2 ifs instead of using || cuz equals throws exception if null
          // when command is null it means the socket has been terminated by the client
          socket.close();
          return;
        }
        if (command.equals("EXIT")) {
          socket.close();
          return;
        }
        if (command.startsWith("AUTH"))
          if (authenticated)
            writer.println("Already authenticated!");
          else {
            String passwordInput = command.replace("AUTH ", "");
            try {
              File passwordFile = new File("./coffeedb_password");
              Scanner passwordFileReader = new Scanner(passwordFile);
              if (passwordFileReader.hasNextLine())
                if (passwordInput.equals(passwordFileReader.nextLine())) {
                  authenticated = true;
                  writer.println("Success");
                } else
                  writer.println("Incorrect password");
              passwordFileReader.close();
            } catch (IOException e) {
              System.out.println("You do not have the permission to read and/or write and/or create the file ./coffeedb_password");
              writer.println("Internal server error");
            }
          }
        else if (authenticated)
          writer.println(Commands.handle(command));
        else
          writer.println("Authenticate with AUTH <password> first");
      } catch (IOException e) {
        System.out.println("Handling client connection failed (IOException) - " + e.getMessage());
      }
    }
  }
}