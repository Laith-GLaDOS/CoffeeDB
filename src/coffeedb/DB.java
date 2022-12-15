package coffeedb;

import ziph.*;
import java.io.*;

public class DB {
  public JSONObject data;

  private File dataFile;

  public DB(String dbFilePath) throws IOException, InvalidJSONException, DataFileNotFoundException {
    this.dataFile = new File(dbFilePath);

    StringBuilder dbStringBuilder = new StringBuilder();
    String currentLine;

    try {
      FileReader dFr = new FileReader(this.dataFile); // dfr stands for data file reader
      BufferedReader dFrBr = new BufferedReader(dFr); // dfrbr stands for data file reader buffered reader

      while ((currentLine = dFrBr.readLine()) != null)
        dbStringBuilder.append(currentLine);

      dFrBr.close();
      dFr.close();
    } catch (FileNotFoundException e) {
      throw new DataFileNotFoundException();
    }

    this.data = new JSONObjectFromString(dbStringBuilder.toString());
  }

  public void save() throws IOException {
    FileWriter dataFileWriter = new FileWriter(this.dataFile);
    dataFileWriter.write(this.data.toJSONString());
    dataFileWriter.close();
  }
}
