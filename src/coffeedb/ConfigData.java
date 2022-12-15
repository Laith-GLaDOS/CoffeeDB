package coffeedb;

public class ConfigData {
  private int port;
  private String password;
  private String dbFilePath;

  public int Port() {
    return this.port;
  }

  public String Password() {
    return this.password;
  }

  public String DbFilePath() {
    return this.dbFilePath;
  }

  public ConfigData(int port, String password, String dbFilePath) {
    this.port = port;
    this.password = password;
    this.dbFilePath = dbFilePath;
  }
}
