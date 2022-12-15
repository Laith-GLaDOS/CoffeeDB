package coffeedb;

import ziph.*;
import java.io.*;

public class DB {
  public static JSONObject data = new JSONObject();

  private static File dataFile;

  public static void load(String dbFilePath) throws IOException, InvalidJSONException, DataFileNotFoundException {
    DB.dataFile = new File(dbFilePath);

    StringBuilder dbStringBuilder = new StringBuilder();
    String currentLine;

    try {
      FileReader dFr = new FileReader(DB.dataFile); // dfr stands for data file reader
      BufferedReader dFrBr = new BufferedReader(dFr); // dfrbr stands for data file reader buffered reader

      while ((currentLine = dFrBr.readLine()) != null)
        dbStringBuilder.append(currentLine);

      dFrBr.close();
      dFr.close();
    } catch (FileNotFoundException e) {
      throw new DataFileNotFoundException();
    }

    DB.data = new JSONObjectFromString(dbStringBuilder.toString());
  }

  public static void save() throws IOException {
    FileWriter dataFileWriter = new FileWriter(DB.dataFile);
    dataFileWriter.write(DB.data.toJSONString());
    dataFileWriter.close();
  }
}
