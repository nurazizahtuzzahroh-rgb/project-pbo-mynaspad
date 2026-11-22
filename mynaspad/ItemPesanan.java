package mynaspad;

/**
 * Satu baris pesanan: menu + jumlah yang dipesan.
 * Subtotal dihitung lewat menu.hitungHarga(...) sehingga perhitungan
 * mengikuti jenis menunya (POLYMORPHISM).
 */
public class ItemPesanan {

    private final MenuNaspad menu;
    private int jumlah;

    public ItemPesanan(MenuNaspad menu, int jumlah) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu tidak boleh kosong.");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0.");
        }
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public MenuNaspad getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void tambahJumlah(int tambahan) {
        if (tambahan <= 0) {
            throw new IllegalArgumentException("Jumlah tambahan harus lebih dari 0.");
        }
        this.jumlah += tambahan;
    }

    /** Subtotal item (dihitung secara polimorfik lewat hitungHarga). */
    public double getSubtotal() {
        return menu.hitungHarga(jumlah);
    }
}
