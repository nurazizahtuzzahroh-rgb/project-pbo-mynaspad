package mynaspad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Class koneksi ke database MySQL mynaspad (Menyesuaikan penamaan file sql yang ada). */
public class Koneksi {

    private static final String URL = "jdbc:mysql://localhost:3306/mynaspad" ;
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
