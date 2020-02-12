package samkt17.fgcu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookStoreDB {

  private Connection connection() {
    Connection conn = null;
    try {
      Class.forName("org.sqlite.JDBC");
      String URL = "jdbc:sqlite:BookStore.db";
      conn = DriverManager.getConnection(URL);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }


}
