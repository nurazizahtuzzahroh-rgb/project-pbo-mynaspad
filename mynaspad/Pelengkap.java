package mynaspad;

/** Subclass 3: Pelengkap (nasi putih, sambal tambahan, kerupuk, dll). Disajikan per pcs. */
public class Pelengkap extends MenuNaspad {

    public Pelengkap(String kodeMenu, String namaMenu, double harga, int stok) {
        super(kodeMenu, namaMenu, harga, stok);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return hargaDasar(jumlah);
    }

    @Override
    public String getKategori() {
        return "Pelengkap";
    }

    @Override
    public String getSatuan() {
        return "pcs";
    }

}
