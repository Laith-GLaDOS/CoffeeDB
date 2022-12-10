import java.net.ServerSocket;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      Logger.log(LogType.ERROR, "Port not specified");
      return;
    }

    int port;

    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      Logger.log(LogType.ERROR, "Port specified is not a number");
      return;
    }

    try {
      File passwordFile = new File("./coffeedb_password");
      if (!passwordFile.exists()) {
        passwordFile.createNewFile();
        FileWriter passwordFileWriter = new FileWriter(passwordFile);
        passwordFileWriter.write("secret");
        passwordFileWriter.close();
        Logger.log(LogType.WARNING, "No ./coffeedb_password file was found, so a new one was created with password \"secret\"");
      }
    } catch (IOException e) {
      Logger.log(LogType.ERROR, "IO error with file ./coffeedb_password - " + e.getMessage());
      return;
    }

    try {
      File dataFile = new File("./coffeedb_data");
      if (!dataFile.exists()) {
        dataFile.createNewFile();
        FileWriter dataFileWriter = new FileWriter(dataFile);
        dataFileWriter.write("");
        dataFileWriter.close();
        Logger.log(LogType.WARNING, "No ./coffeedb_data file was found, so an empty one was created");
      }
    } catch (IOException e) {
      Logger.log(LogType.ERROR, "IO error with file ./coffeedb_data - " + e.getMessage());
      return;
    }

    //if (DB.loadFromFile() == 1) return;

    if (port < 1) {
      Logger.log(LogType.ERROR, "Port must be above or equal to 1");
      return;
    }

    if (port > 65535) {
      Logger.log(LogType.ERROR, "Port must be less than 65535");
      return;
    }

    try (ServerSocket server = new ServerSocket(port)) {
      Logger.log(LogType.NORMAL, "Server started");
      while (true) try {
        ConnectionThread thread = new ConnectionThread(server.accept());
        thread.start();
        Logger.log(LogType.NORMAL, "Connection accepted");
      } catch (IOException e) {
        Logger.log(LogType.ERROR, "Accepting client connection failed (IOException) - " + e.getMessage());
      }
    } catch (IOException e) {
      Logger.log(LogType.ERROR, "Server failed to start (IOException) - " + e.getMessage());
    }
  }
}
