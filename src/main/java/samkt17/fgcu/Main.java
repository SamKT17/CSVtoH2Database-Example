package samkt17.fgcu;

import com.opencsv.exceptions.CsvException;
import java.io.IOException;


public class Main {
  public static void main(String[] args) throws IOException, CsvException {

    BookStoreDB bsdb = new BookStoreDB();
    bsdb.csvToDB();
    bsdb.jsonToDB();
  }
}
