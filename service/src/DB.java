import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DB {
  public static List<KeyValueObject> data = new ArrayList<KeyValueObject>();
  public static int loadFromFile() {
    File dataFile = new File("./coffeedb_data");
    try {
      Scanner dataFileReader = new Scanner(dataFile);
      if (dataFileReader.hasNextLine()) {
        String newData = dataFileReader.nextLine();
        String[] newDataAsArray = newData.split("\0");
        for (int i = 0; i < newDataAsArray.length; i++) {
          String type = newDataAsArray[i].split(" ")[0];
          String key = newDataAsArray[i].split(" ")[1];
          String value = newDataAsArray[i].replaceAll(type + " " + key + " ", "");
          data.add(new KeyValueObject(type, key, value));
        }
      }
      dataFileReader.close();
    } catch (FileNotFoundException e) {
      try {
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
      dataFile.createNewFile();
      FileWriter dataFileWriter = new FileWriter(dataFile);
      String dataToWrite = "";
      for (int i = 0; i < data.size(); i++)
        dataToWrite += data.get(i).type + " " + data.get(i).key + " " + data.get(i).value + "\0";
      dataFileWriter.write(dataToWrite);
      dataFileWriter.close();
    } catch (IOException e) {
      System.out.println("IO error with file ./coffeedb_data - " + e.getMessage());
      return 1;
    }
    return 0;
  }
}