import java.net.ServerSocket;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Port not specified");
      return;
    }

    int port;

    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      System.out.println("Port specified is not a number");
      return;
    }

    try {
      File passwordFile = new File("./coffeedb_password");
      if (!passwordFile.exists()) {
        passwordFile.createNewFile();
        FileWriter passwordFileWriter = new FileWriter(passwordFile);
        passwordFileWriter.write("secret");
        passwordFileWriter.close();
      }
    } catch (IOException e) {
      System.out.println("IO error with file ./coffeedb_password - " + e.getMessage());
      return;
    }

    try {
      File dataFile = new File("./coffeedb_data");
      if (!dataFile.exists()) {
        dataFile.createNewFile();
        FileWriter dataFileWriter = new FileWriter(dataFile);
        dataFileWriter.write("");
        dataFileWriter.close();
      }
    } catch (IOException e) {
      System.out.println("IO error with file ./coffeedb_data - " + e.getMessage());
      return;
    }

    if (DB.loadFromFile() == 1) return;

    if (port < 1) {
      System.out.println("Port must be above 1");
      return;
    }

    if (port > 65535) {
      System.out.println("Port must be less than 65535");
      return;
    }

    try (ServerSocket server = new ServerSocket(port)) {
      System.out.println("Started server");
      while (true) {
        try {
          ConnectionThread thread = new ConnectionThread(server.accept());
          thread.start();
        } catch (IOException e) {
          System.out.println("Handling client connection failed (IOException) - " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.out.println("Server failed to start (IOException) - " + e.getMessage());
      return;
    }
  }
}