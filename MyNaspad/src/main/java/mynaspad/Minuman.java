package mynaspad;

/** Subclass 2: Minuman (es teh, teh talua, es jeruk, dll). Disajikan per gelas. */
public class Minuman extends MenuNaspad {

    public Minuman(String kodeMenu, String namaMenu, double harga, int stok) {
        super(kodeMenu, namaMenu, harga, stok);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return hargaDasar(jumlah);
    }

    @Override
    public String getKategori() {
        return "Minuman";
    }

    @Override
    public String getSatuan() {
        return "gelas";
    }

    @Override
    public String getInfoMenu() {
        return "Minuman penyegar, per gelas";
    }
}
