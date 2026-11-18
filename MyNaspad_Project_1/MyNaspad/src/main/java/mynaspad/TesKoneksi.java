package mynaspad;

import java.sql.Connection;
import java.sql.SQLException;

/** Menguji koneksi database (jalankan class ini lebih dulu). */
public class TesKoneksi {

    public static void main(String[] args) {
        try (Connection conn = Koneksi.getKoneksi()) {
            System.out.println("Koneksi database berhasil!");
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
    }
}
