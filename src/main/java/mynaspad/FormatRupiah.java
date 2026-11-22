package mynaspad;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
/**
 * Class untuk mengubah angka menjadi format mata uang Rupiah.
 * Digunakan untuk menampilkan harga, total pembayaran,
 * dan uang kembalian.
 */
public class FormatRupiah {

    private final DecimalFormat Format;
    
    public FormatRupiah() {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();

        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');

        Format = new DecimalFormat("#,##0", simbol);
    }

    public String format(double nilai) {
        return "Rp " + Format.format(nilai);
    }
}

