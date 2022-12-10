import java.util.Date;

public class Logger {
  public Logger() {}
  public static void log(LogType logType, String log) {
    switch (logType) {
      case NORMAL -> System.out.println("(" + new Date() + ") (Normal)  " + log);
      case WARNING -> System.out.println("(" + new Date() + ") (Warning) " + log);
      case ERROR -> System.out.println("(" + new Date() + ") (Error)   " + log);
    }
  }
}
