
public class Commands {
  public static String handle(String command) {
    String[] commandAndArgs = command.split(" ");
    switch (commandAndArgs[0]) {
      case "GET":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        String GETReturnValue = GET(commandAndArgs[1]);
        System.out.println("GET " + commandAndArgs[1] + " -> " + GETReturnValue);
        return GETReturnValue;

      case "GETKEYS":
        if (commandAndArgs.length != 1)
          return "Bad arguments";
        String GETKEYSReturnValue = GETKEYS();
        System.out.println("GETKEYS -> " + GETKEYSReturnValue);
        return GETKEYSReturnValue;

      case "SET":
        if (commandAndArgs.length < 4)
          return "Bad arguments";
        String SETReturnValue = SET(commandAndArgs[1], commandAndArgs[2], command.replace("SET " + commandAndArgs[1] + " " + commandAndArgs[2] + " " , ""));
        System.out.println("SET " + commandAndArgs[1] + " " + commandAndArgs[2] + " " + command.replace("SET " + commandAndArgs[1] + " " + commandAndArgs[2] + " " , "") + " -> " + SETReturnValue);
        return SETReturnValue;

      case "DELETE":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        String DELETEReturnValue = DELETE(commandAndArgs[1]);
        System.out.println("DELETE " + commandAndArgs[1] + " -> " + DELETEReturnValue);
        return DELETEReturnValue;

      default:
        System.out.println(command + " -> Command not found");
        return "Command not found";
    }
  }

  private static String GET(String key) {
    for (int i = 0; i < DB.data.size(); i++)
      if (DB.data.get(i).key.equals(key))
        return DB.data.get(i).type + " " + DB.data.get(i).value;

    return "Key not found";
  }

  private static String GETKEYS() {
    String keys = "[";
    for (int i = 0; i < DB.data.size(); i++)
      keys += "\"" + DB.data.get(i).key + "\"" + (i != DB.data.size() - 1 ? ", " : "");

    return keys + "]";
  }

  private static String SET(String key, String type, String value) {
    boolean setInsteadOfAdd = false;
    int setIndex = 0;

    for (int i = 0; i < DB.data.size(); i++)
      if (DB.data.get(i).key.equals(key)) {
        setInsteadOfAdd = true;
        setIndex = i;
      }

    switch (type) {
      case "bool":
        if (value != "true" && value != "false")
          return "Invalid value for bool";

        if (setInsteadOfAdd)
          DB.data.set(setIndex, new KeyValueObject(key, value, type));
        else
          DB.data.add(new KeyValueObject(key, value, type));

        break;

      case "int":
        try {
          Integer.parseInt(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, value, type));
          else
            DB.data.add(new KeyValueObject(key, value, type));
        } catch (Exception e) {
          return "Invalid value for int";
        }
        break;

      case "float":
        try {
          Float.parseFloat(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, value, type));
          else
            DB.data.add(new KeyValueObject(key, value, type));
        } catch (Exception e) {
          return "Invalid value for float";
        }
        break;

      case "double":
        try {
          Double.parseDouble(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, value, type));
          else
            DB.data.add(new KeyValueObject(key, value, type));
        } catch (Exception e) {
          return "Invalid value for double";
        }
        break;

      case "string":
        if (setInsteadOfAdd)
          DB.data.set(setIndex, new KeyValueObject(key, value, type));
        else
          DB.data.add(new KeyValueObject(key, value, type));
        break;

      default:
        return "Unknown datatype";
    }

    DB.saveToFile();
    return "Success";
  }

  private static String DELETE(String key) {
    for (int i = 0; i < DB.data.size(); i++)
      if (DB.data.get(i).key.equals(key)) {
        DB.data.remove(i);
        DB.saveToFile();
        return "Success";
      }

    return "Key not found";
  }
}
