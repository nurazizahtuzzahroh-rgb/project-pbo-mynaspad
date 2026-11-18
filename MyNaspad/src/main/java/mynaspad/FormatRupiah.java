package mynaspad;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Class bantu untuk menampilkan angka sebagai Rupiah, contoh: Rp 25.000 */
public class FormatRupiah {

    private static final DecimalFormat FORMAT;

    static {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols(Locale.forLanguageTag("id-ID"));
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        FORMAT = new DecimalFormat("#,##0", simbol);
    }

    private FormatRupiah() {
    }

    public static String format(double nilai) {
        return "Rp " + FORMAT.format(nilai);
    }
}
