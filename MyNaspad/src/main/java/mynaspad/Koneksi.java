package mynaspad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class koneksi ke database MySQL db_my_naspad. */
public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/db_my_naspad";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
