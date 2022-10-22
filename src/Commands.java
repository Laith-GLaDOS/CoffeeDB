
public class Commands {
  public static String handle(String command) {
    String[] commandAndArgs = command.split(" ");
    switch (commandAndArgs[0]) {
      case "GET":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        return GET(commandAndArgs[1]);
      
      case "SET":
        if (commandAndArgs.length != 3)
          return "Bad arguments";
        return SET(commandAndArgs[1], commandAndArgs[2]);

      default:
        return "Command not found";
    }
  }

  public static String GET(String key) {
    for (int i = 0; i < DataArray.data.size(); i++) {
      if (DataArray.data.get(i).key.equals(key))
        return DataArray.data.get(i).value;
    }

    return "Key not found";
  }

  public static String SET(String key, String value) {
    for (int i = 0; i < DataArray.data.size(); i++)
      if (DataArray.data.get(i).key.equals(key))
        DataArray.data.set(i, new KeyValueObject(key, value));

    DataArray.data.add(new KeyValueObject(key, value));

    return "Success";
  }
}
