package coffeedb;
import java.io.*;
import ziph.*;

public class Config {
  private File configFile;
  public ConfigData data;

  public Config(String configFilePath) throws ConfigFileNotFoundException, InvalidJSONException, InvalidConfigException, IOException {
    this.configFile = new File(configFilePath);

    StringBuilder configStringBuilder = new StringBuilder();
    String currentLine;

    try {
      FileReader configFr = new FileReader(this.configFile);
      BufferedReader configFrBr = new BufferedReader(configFr);

      while ((currentLine = configFrBr.readLine()) != null)
        configStringBuilder.append(currentLine);

      configFrBr.close();
      configFr.close();
    } catch (FileNotFoundException e) {
      throw new ConfigFileNotFoundException();
    }

    JSONObject configObject = new JSONObjectFromString(configStringBuilder.toString());

    if (configObject.get("port") == null)
      throw new InvalidConfigException();

    if (!(configObject.get("port") instanceof Integer))
      throw new InvalidConfigException();

    if (configObject.get("password") == null)
      throw new InvalidConfigException();

    if (!(configObject.get("password") instanceof String))
      throw new InvalidConfigException();

    if (configObject.get("dbFilePath") != null) {
      if (!(configObject.get("dbFilePath") instanceof String))
        throw new InvalidConfigException();
    } else configObject.set("dbFilePath", "./coffeedb_data.json");

    this.data = new ConfigData((int)configObject.get("port"), (String)configObject.get("password"), (String)configObject.get("dbFilePath"));
  }
}
