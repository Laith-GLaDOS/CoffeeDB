import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Commands {
  public static String handle(String command) {
    String[] commandAndArgs = command.split(" ");
    switch (commandAndArgs[0]) {
      case "GET":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        return GET(commandAndArgs[1]);

      case "GETKEYS":
        if (commandAndArgs.length != 1)
          return "Bad arguments";
        return GETKEYS();

      case "SET":
        if (commandAndArgs.length < 4)
          return "Bad arguments";
        return SET(commandAndArgs[1], commandAndArgs[2], command.replace("SET " + commandAndArgs[1] + " " + commandAndArgs[2] + " " , ""));

      case "DELETE":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        return DELETE(commandAndArgs[1]);

      case "EXPIRE":
        if (commandAndArgs.length != 4)
          return "Bad arguments";
        if (!commandAndArgs[2].equals("IN"))
          return "Bad arguments";
        return EXPIRE(commandAndArgs[1], commandAndArgs[3]);

      default:
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
          DB.data.set(setIndex, new KeyValueObject(key, type, value));
        else
          DB.data.add(new KeyValueObject(key, type, value));

        break;

      case "int":
        try {
          Integer.parseInt(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, type, value));
          else
            DB.data.add(new KeyValueObject(key, type, value));
        } catch (Exception e) {
          return "Invalid value for int";
        }
        break;

      case "float":
        try {
          Float.parseFloat(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, type, value));
          else
            DB.data.add(new KeyValueObject(key, type, value));
        } catch (Exception e) {
          return "Invalid value for float";
        }
        break;

      case "double":
        try {
          Double.parseDouble(value);
          if (setInsteadOfAdd)
            DB.data.set(setIndex, new KeyValueObject(key, type, value));
          else
            DB.data.add(new KeyValueObject(key, type, value));
        } catch (Exception e) {
          return "Invalid value for double";
        }
        break;

      case "string":
        if (setInsteadOfAdd)
          DB.data.set(setIndex, new KeyValueObject(key, type, value));
        else
          DB.data.add(new KeyValueObject(key, type, value));
        break;

      default:
        return "Unknown datatype";
    }

    if (DB.saveToFile() == 1) return "Internal server error";
    return "Success";
  }

  private static String DELETE(String key) {
    for (int i = 0; i < DB.data.size(); i++)
      if (DB.data.get(i).key.equals(key)) {
        DB.data.remove(i);
        if (DB.saveToFile() == 1) return "Internal server error";
        return "Success";
      }

    return "Key not found";
  }

  private static String EXPIRE(String keyToExpire, String howManyMsBeforeExpire) {
    if (GET(keyToExpire) == "Key not found")
      return "Key not found";
    int howManyMsBeforeExpireInt = 0;
    try {
      howManyMsBeforeExpireInt = Integer.parseInt(howManyMsBeforeExpire);
    } catch (Exception e) {
      return "Invalid millisecond value";
    }
    CompletableFuture.delayedExecutor(howManyMsBeforeExpireInt, TimeUnit.MILLISECONDS).execute(() -> DELETE(keyToExpire));
    return "Success";
  }
}
