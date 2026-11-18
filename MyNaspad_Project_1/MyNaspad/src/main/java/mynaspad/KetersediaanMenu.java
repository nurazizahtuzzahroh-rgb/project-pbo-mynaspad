package mynaspad;

/**
 * INTERFACE: mendefinisikan perilaku pengelolaan ketersediaan (stok) menu.
 * Setiap menu harus bisa menerima stok masuk, mengeluarkan stok,
 * dan dicek apakah jumlah yang diminta masih tersedia.
 */
public interface KetersediaanMenu {

    /** Menambah stok menu. */
    //void stokMasuk(int jumlah);

    /**
     * Mengurangi stok menu.
     * @return true jika stok cukup dan berhasil dikurangi, false jika gagal
     */
    boolean stokKeluar(int jumlah);

    /** Mengecek apakah jumlah yang diminta masih tersedia. */
    boolean isTersedia(int jumlah);
}
