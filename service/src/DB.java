/*import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;*/
import ziph.*;

public class DB {
  public static JSONObject data = new JSONObject();
  //public static List<KeyValue> data = new ArrayList<>();
  /*public static int loadFromFile() {
    File dataFile = new File("./coffeedb_data");
    try {
      Scanner dataFileReader = new Scanner(dataFile);
      if (dataFileReader.hasNextLine()) {
        String newData = dataFileReader.nextLine();
        String[] newDataAsArray = newData.split("\0");
        for (String s : newDataAsArray) {
          String[] newDataChunk = s.split(" ");
          String key = newDataChunk[0];
          String type = newDataChunk[1];
          String value = s.replaceAll(key + " " + type + " ", "");
          data.add(new KeyValue(key, type, value));
        }
      }
      dataFileReader.close();
    } catch (FileNotFoundException e) {
      try {
        //noinspection ResultOfMethodCallIgnored
        dataFile.createNewFile();
        loadFromFile();
      } catch (IOException e2) {
        System.out.println("IO error with file ./coffeedb_data - " + e2.getMessage());
        return 1;
      }
    }
    return 0;
  }
  public static int saveToFile() {
    File dataFile = new File("./coffeedb_data");
    try {
      //noinspection ResultOfMethodCallIgnored
      dataFile.createNewFile();
      FileWriter dataFileWriter = new FileWriter(dataFile);
      StringBuilder dataToWrite = new StringBuilder();
      for (KeyValue datum : data)
        dataToWrite.append(datum.key).append(" ").append(datum.type).append(" ").append(datum.value).append("\0");
      dataFileWriter.write(dataToWrite.toString());
      dataFileWriter.close();
    } catch (IOException e) {
      System.out.println("IO error with file ./coffeedb_data - " + e.getMessage());
      return 1;
    }
    return 0;
  }*/
}
