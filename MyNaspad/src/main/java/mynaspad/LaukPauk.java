package mynaspad;

/** Subclass 1: Lauk Pauk (rendang, ayam pop, gulai, dll). Disajikan per porsi. */
public class LaukPauk extends MenuNaspad {

    public LaukPauk(String kodeMenu, String namaMenu, double harga, int stok) {
        super(kodeMenu, namaMenu, harga, stok);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return hargaDasar(jumlah);
    }

    @Override
    public String getKategori() {
        return "Lauk Pauk";
    }

    @Override
    public String getSatuan() {
        return "porsi";
    }

    @Override
    public String getInfoMenu() {
        return "Lauk khas Padang, per porsi";
    }
}
