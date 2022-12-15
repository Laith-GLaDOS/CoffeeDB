package coffeedb;

import java.io.IOException;
import java.net.ServerSocket;
import ziph.InvalidJSONException;

public class Main {
  public static void main(String[] args) {
    String configFilePath = args.length >= 1 ? args[0] : "./coffeedb_config.json";
    Config config = null;

    Logger.log(LogType.NORMAL, new StringBuilder("Loading config file (").append(configFilePath).append(")...").toString());

    try {
      config = new Config(configFilePath);
    } catch (ConfigFileNotFoundException e) {
      Logger.log(LogType.ERROR, "Config file not found");
      return;
    } catch (InvalidJSONException e) {
      Logger.log(LogType.ERROR, "Config file has invalid JSON");
      return;
    } catch (InvalidConfigException e) {
      Logger.log(LogType.ERROR, "Config file has invalid configuration");
      return;
    } catch (IOException e) {
      Logger.log(LogType.ERROR, new StringBuilder("IO error while reading config file - ").append(e.getMessage()).toString());
      return;
    }

    Logger.log(LogType.NORMAL, new StringBuilder("port = ").append(config.data.Port()).toString());
    Logger.log(LogType.NORMAL, new StringBuilder("password = ").append(config.data.Password()).toString());
    Logger.log(LogType.NORMAL, new StringBuilder("dbFilePath = ").append(config.data.DbFilePath()).toString());

    try (ServerSocket server = new ServerSocket(config.data.Port())) {
      Logger.log(LogType.NORMAL, "Server started");
      while (true) try {
        ConnectionThread thread = new ConnectionThread(server.accept(), config.data.Password());
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
