package mynaspad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) untuk tabel menu.
 * Data menu diisi langsung di database (lihat database/db_my_naspad.sql),
 * sehingga DAO ini hanya membaca menu untuk ditampilkan di form pemesanan.
 */
public class MenuDAO {

    /**
     * Factory method: membuat objek subclass yang sesuai dengan kategori.
     * Hasilnya bertipe MenuNaspad (POLYMORPHISM).
     */
    public static MenuNaspad buatMenu(String kategori, String kode, String nama,
                                      double harga, int stok) {
        switch (kategori) {
            case "Lauk Pauk":
                return new LaukPauk(kode, nama, harga, stok);
            case "Minuman":
                return new Minuman(kode, nama, harga, stok);
            case "Pelengkap":
                return new Pelengkap(kode, nama, harga, stok);
            default:
                throw new IllegalArgumentException("Kategori tidak dikenal: " + kategori);
        }
    }

    private MenuNaspad dariResultSet(ResultSet rs) throws SQLException {
        return buatMenu(rs.getString("kategori"), rs.getString("kode_menu"),
                rs.getString("nama_menu"), rs.getDouble("harga"), rs.getInt("stok"));
    }

    public List<MenuNaspad> getSemuaMenu() throws SQLException {
        List<MenuNaspad> hasil = new ArrayList<>();
        String sql = "SELECT * FROM menu ORDER BY kategori, nama_menu";
        try (Connection conn = Koneksi.getKoneksi();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                hasil.add(dariResultSet(rs));
            }
        }
        return hasil;
    }

    public MenuNaspad getByKode(String kode) throws SQLException {
        String sql = "SELECT * FROM menu WHERE kode_menu = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? dariResultSet(rs) : null;
            }
        }
    }
}
