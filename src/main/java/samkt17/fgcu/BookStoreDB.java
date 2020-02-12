package samkt17.fgcu;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookStoreDB {

  private Connection connection() {
    Connection conn = null;
    try {
      Class.forName("org.sqlite.JDBC");
      String URL = "jdbc:sqlite:./src/Data/BookStore";
      conn = DriverManager.getConnection(URL);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public void csvToDB() throws IOException, CsvValidationException {

    Reader read = Files.newBufferedReader(Paths.get("src/Data/bookstore_report2.csv"));
    CSVReader csvr = new CSVReader(read);

    String[] fileInfo;
    while ((fileInfo = csvr.readNext()) != null) {
      String sql =
          "INSERT INTO book(isbn, book_title, author_name, publisher_name) "
              + "VALUES (?, ?, ?, ?)";

      try {

        PreparedStatement ps = connection().prepareStatement(sql);
        ps.setString(1, fileInfo[0]);
        ps.setString(2, fileInfo[1]);
        ps.setString(3, fileInfo[2]);
        ps.setString(4, fileInfo[3]);

        ps.executeUpdate();

        ps.close();
        connection().close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void jsonToDB() throws FileNotFoundException {
    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    String sql = "INSERT INTO author(author_name, author_email, author_url) VALUES (?, ?, ?)";

    try {

      PreparedStatement ps = connection().prepareStatement(sql);

      for (var element : authors) {
        ps.setString(1, element.getName());
        ps.setString(2, element.getEmail());
        ps.setString(3, element.getUrl());
        ps.executeUpdate();
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
