import java.util.Date;

public class Logger {
  public Logger() {}
  public static void log(LogType logType, String log) {
    switch (logType) {
      case NORMAL:
        System.out.println("(" + (new Date()).toString() + ") (Normal)  " + log);
        break;
      case WARNING:
        System.out.println("(" + (new Date()).toString() + ") (Warning) " + log);
        break;
      case ERROR:
        System.out.println("(" + (new Date()).toString() + ") (Error)   " + log);
        break;
    }
  }
}
