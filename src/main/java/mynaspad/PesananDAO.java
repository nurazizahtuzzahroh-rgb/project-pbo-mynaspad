package mynaspad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/** DAO untuk menyimpan transaksi pesanan ke tabel pesanan dan detail_pesanan. */
public class PesananDAO {

    /**
     * Menyimpan pesanan dalam satu transaksi database:
     * 1) insert ke tabel pesanan, 2) insert tiap item ke detail_pesanan,
     * 3) kurangi stok menu. Bila ada yang gagal (misal stok tidak cukup),
     * semua dibatalkan (rollback).
     *
     * @return nomor pesanan (id_pesanan) yang baru dibuat
     */
    public int simpan(Pesanan pesanan, double bayar) throws SQLException {
        String sqlPesanan = "INSERT INTO pesanan "+ "(nama_pelanggan, tanggal, total, bayar, kembalian) VALUES (?, ?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_pesanan "+ "(id_pesanan, kode_menu, jumlah, subtotal) VALUES (?, ?, ?, ?)";
        String sqlStok = "UPDATE menu SET stok = stok - ? WHERE kode_menu = ? AND stok >= ?";

        try (Connection conn = Koneksi.getKoneksi()) {
            conn.setAutoCommit(false);
            try {
                int idPesanan;
                try (PreparedStatement ps = conn.prepareStatement(sqlPesanan,
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, pesanan.getNamaPelanggan());
                    ps.setTimestamp(2, Timestamp.valueOf(pesanan.getTanggal()));
                    ps.setDouble(3, pesanan.hitungTotal());
                    ps.setDouble(4, bayar);
                    ps.setDouble(5, pesanan.hitungKembalian(bayar));
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Gagal mendapatkan nomor pesanan.");
                        }
                        idPesanan = rs.getInt(1);
                    }
                }

                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                     PreparedStatement psStok = conn.prepareStatement(sqlStok)) {
                    for (ItemPesanan item : pesanan.getDaftarItem()) {
                        MenuNaspad menu = item.getMenu();

                        psStok.setInt(1, item.getJumlah());
                        psStok.setString(2, menu.getKodeMenu());
                        psStok.setInt(3, item.getJumlah());
                        if (psStok.executeUpdate() == 0) {
                            throw new SQLException("Stok " + menu.getNamaMenu()
                                    + " tidak mencukupi.");
                        }

                        psDetail.setInt(1, idPesanan);
                        psDetail.setString(2, menu.getKodeMenu());
                        psDetail.setInt(3, item.getJumlah());
                        psDetail.setDouble(4, item.getSubtotal());
                        psDetail.executeUpdate();
                    }
                }

                conn.commit();
                return idPesanan;
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
