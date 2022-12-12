package coffeedb;

import java.io.IOException;
import ziph.InvalidJSONException;

public class Main {
  public static void main(String[] args) {
    String configFilePath = args.length >= 1 ? args[0] : "./coffeedb_config.json";
    Config config = null;

    try {
      config = new Config(configFilePath);
    } catch (ConfigFileNotFoundException e) {
      Logger.log(LogType.ERROR, new StringBuilder("Config file (").append(configFilePath).append(") not found").toString());
      return;
    } catch (InvalidJSONException e) {
      Logger.log(LogType.ERROR, new StringBuilder("Config file (").append(configFilePath).append(") has invalid JSON").toString());
      return;
    } catch (InvalidConfigException e) {
      Logger.log(LogType.ERROR, new StringBuilder("Config file (").append(configFilePath).append(") has invalid configuration").toString());
      return;
    } catch (IOException e) {
      Logger.log(LogType.ERROR, new StringBuilder("IO error while reading config file (").append(configFilePath).append(") - ").append(e.getMessage()).toString());
      return;
    }
  }
}
