package mynaspad;

/**
 * ABSTRACT CLASS (superclass) untuk semua menu My Naspad.
 *
 * - ENCAPSULATION : atribut bersifat private, diakses lewat getter/setter
 *                   yang memvalidasi data.
 * - INTERFACE     : mengimplementasikan KetersediaanMenu (pengelolaan stok).
 * - ABSTRACT      : hitungHarga(), getKategori(), getSatuan(), getInfoMenu()
 *                   wajib di-override oleh subclass (POLYMORPHISM).
 */
public abstract class MenuNaspad implements KetersediaanMenu {

    private final String kodeMenu; 
    //final pada kodeMenu: nilainya hanya bisa diisi sekali (di constructor) dan tidak bisa diubah lagi. 
    private String namaMenu;
    private double harga;
    private int stok;

    public MenuNaspad(String kodeMenu, String namaMenu, double harga, int stok) {
        if (kodeMenu == null || kodeMenu.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode menu tidak boleh kosong.");
        }
        this.kodeMenu = kodeMenu.trim();
        setNamaMenu(namaMenu);
        setHarga(harga);
        setStok(stok);
    }

    // ---------- Getter ----------
    public String getKodeMenu() {
        return kodeMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    // ---------- Setter dengan validasi (encapsulation) ----------
    public final void setNamaMenu(String namaMenu) {
        if (namaMenu == null || namaMenu.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama menu tidak boleh kosong.");
        }
        this.namaMenu = namaMenu.trim();
    }

    public final void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        }
        this.harga = harga;
    }

    public final void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.stok = stok;
    }

    // ---------- Implementasi interface KetersediaanMenu ----------
    /*@Override
    public void stokMasuk(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah stok masuk harus lebih dari 0.");
        }
        stok += jumlah;
    }*/

    @Override
    public boolean stokKeluar(int jumlah) {
        if (!isTersedia(jumlah)) {
            return false;
        }
        stok -= jumlah;
        return true;
    }

    @Override
    public boolean isTersedia(int jumlah) {
        return jumlah > 0 && jumlah <= stok;
    }

    // ---------- Method abstract (di-override subclass) ----------

    /** Menghitung harga untuk sejumlah porsi. */
    public abstract double hitungHarga(int jumlah);

    /** Nama kategori; juga disimpan ke kolom "kategori" di database. */
    public abstract String getKategori();

    /** Satuan penyajian: porsi, gelas, atau pcs. */
    public abstract String getSatuan();

    /** Keterangan singkat jenis menu, ditampilkan di form pemesanan. */
    public abstract String getInfoMenu();

    // ---------- Method bantu untuk subclass ----------
    /*protected double hargaDasar(int jumlah) {
        return harga * jumlah;
    }*/

    @Override
    public String toString() {
        return kodeMenu + " - " + namaMenu + " (" + getKategori() + ")";
    }
}
