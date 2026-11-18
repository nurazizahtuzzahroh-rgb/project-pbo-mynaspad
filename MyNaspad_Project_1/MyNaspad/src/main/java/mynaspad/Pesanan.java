package mynaspad;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Satu transaksi pemesanan: nama pelanggan + keranjang berisi ItemPesanan.
 * Class ini menjalankan PROSES perhitungan (total, kembalian) dan
 * membuat OUTPUT berupa struk.
 */
public class Pesanan {

    private String namaPelanggan = "";
    private final LocalDateTime tanggal = LocalDateTime.now();
    private final List<ItemPesanan> daftarItem = new ArrayList<>();

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama pelanggan tidak boleh kosong.");
        }
        this.namaPelanggan = namaPelanggan.trim();
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    /** Daftar item hanya bisa dibaca dari luar (encapsulation). */
    public List<ItemPesanan> getDaftarItem() {
        return Collections.unmodifiableList(daftarItem);
    }

    public boolean isKosong() {
        return daftarItem.isEmpty();
    }

    /** Menambah menu ke keranjang; bila menu sama sudah ada, jumlahnya digabung. */
    public void tambahItem(MenuNaspad menu, int jumlah) {
        for (ItemPesanan item : daftarItem) {
            if (item.getMenu().getKodeMenu().equals(menu.getKodeMenu())) {
                item.tambahJumlah(jumlah);
                return;
            }
        }
        daftarItem.add(new ItemPesanan(menu, jumlah));
    }

    public void hapusItem(int index) {
        if (index >= 0 && index < daftarItem.size()) {
            daftarItem.remove(index);
        }
    }

    /** Jumlah suatu menu yang sudah ada di keranjang. */
    public int getJumlahMenu(String kodeMenu) {
        for (ItemPesanan item : daftarItem) {
            if (item.getMenu().getKodeMenu().equals(kodeMenu)) {
                return item.getJumlah();
            }
        }
        return 0;
    }

    // ---------- Perhitungan ----------
    public double hitungTotal() {
        double total = 0;
        for (ItemPesanan item : daftarItem) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double hitungKembalian(double bayar) {
        return bayar - hitungTotal();
    }

    // ---------- Output ----------
    public String buatStruk(int idPesanan, double bayar) {
        String garis = "----------------------------------------\n";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("               MY NASPAD\n");
        sb.append("        Nasi Padang Pilihan Anda\n");
        sb.append(garis);
        sb.append(String.format("No. Pesanan : %d%n", idPesanan));
        sb.append(String.format("Tanggal     : %s%n", tanggal.format(fmt)));
        sb.append(String.format("Pelanggan   : %s%n", namaPelanggan));
        sb.append(garis);
        for (ItemPesanan item : daftarItem) {
            MenuNaspad m = item.getMenu();
            sb.append(m.getNamaMenu()).append("\n");
            String kiri = "  " + item.getJumlah() + " " + m.getSatuan() + " x "
                    + FormatRupiah.format(m.getHarga());
            sb.append(String.format("%-26s%14s%n", kiri, FormatRupiah.format(item.getSubtotal())));
        }
        sb.append(garis);
        sb.append(String.format("%-20s %19s%n", "TOTAL", FormatRupiah.format(hitungTotal())));
        sb.append(String.format("%-20s %19s%n", "BAYAR", FormatRupiah.format(bayar)));
        sb.append(String.format("%-20s %19s%n", "KEMBALIAN", FormatRupiah.format(hitungKembalian(bayar))));
        sb.append(garis);
        sb.append("     Terima kasih, selamat menikmati!\n");
        return sb.toString();
    }
}
