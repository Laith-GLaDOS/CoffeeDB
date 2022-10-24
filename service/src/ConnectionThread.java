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
        String command = new BufferedReader(new InputStreamReader(this.socket.getInputStream())).readLine();

        PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
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
            writer.write("Already authenticated!");
          else {
            String passwordInput = command.replace("AUTH ", "");
            try {
              File passwordFile = new File("./coffeedb_password");
              Scanner passwordFileScanner = new Scanner(passwordFile);
              if (passwordFileScanner.hasNextLine())
                if (passwordInput.equals(passwordFileScanner.nextLine())) {
                  authenticated = true;
                  writer.write("Success");
                } else
                  writer.write("Incorrect password");
                  passwordFileScanner.close();
            } catch (IOException e) {
              System.out.println("You do not have the permission to read and/or write and/or create the file ./coffeedb_password");
              writer.write("Internal server error");
            }
          }
        else if (authenticated)
          writer.write(Commands.handle(command));
        else
          writer.write("Authenticate with AUTH <password> first");
        writer.flush();
      } catch (IOException e) {
        System.out.println("Handling client connection failed (IOException) - " + e.getMessage());
      }
    }
  }
}
