
public class Commands {
  public static Object handle(String command) {
    String[] commandAndArgs = command.split(" ");
    switch (commandAndArgs[0]) {
      case "GET":
        if (commandAndArgs.length != 2)
          return "Bad arguments";
        return GET(commandAndArgs[1]);

      default:
        return "Command not found";
    }
  }

  public static Object GET(String key) {
    return key;
  }
}
