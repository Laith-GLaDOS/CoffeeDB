package coffeedb;

import java.io.IOException;
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

    Logger.log(LogType.NORMAL, new StringBuilder("port = ").append(config.data.port).toString());
    Logger.log(LogType.NORMAL, new StringBuilder("password = ").append(config.data.password).toString());
  }
}
