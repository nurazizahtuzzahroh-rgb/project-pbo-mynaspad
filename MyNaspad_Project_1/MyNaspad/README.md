# My Naspad: Aplikasi Pemesanan Nasi Padang

Project UAS Paradigma Berorientasi Objek. Java + JFrame (Swing) + MySQL, mengikuti pola modul Toko ATK.
Aplikasi hanya melayani sisi pembeli (pemesanan). Data menu dikelola langsung di database.

## Cara menjalankan

1. **Database**: buka MySQL Workbench atau phpMyAdmin, jalankan isi `database/db_my_naspad.sql`.
   Menu, harga, dan stok diubah langsung di tabel `menu` (contoh perintah ada di akhir file SQL).
2. **NetBeans**: File → Open Project → pilih folder `MyNaspad` (project Maven).
   Dependency `mysql-connector-j` 9.7.0 diunduh otomatis dari `pom.xml`, jadi perlu internet saat build pertama.
3. Bila password MySQL tidak kosong, ubah konstanta `PASSWORD` di `Koneksi.java`.
4. Jalankan `TesKoneksi.java` lebih dulu. Hasil yang benar: `Koneksi database berhasil!`
5. Jalankan `MyNaspad.java` (main). Menu utama: **PEMESANAN** dan **KELUAR**.
   Di form pemesanan: isi nama pelanggan, pilih menu dan jumlah, tambah ke keranjang,
   isi uang bayar, lalu **PROSES & SIMPAN PESANAN**. Struk muncul setelah pesanan tersimpan.

## Form JFrame (bisa diubah di tab Design)

`FormUtama` dan `FormPesan` masing-masing punya pasangan file `.java` dan `.form`.
Buka file `.java` di NetBeans lalu klik tab **Design** untuk mengubah tata letak dengan drag-and-drop.
Isi `initComponents()` dibuat otomatis oleh NetBeans; kode buatan sendiri (mengisi tabel, menghitung)
berada di luar blok itu dan tidak ikut tertimpa.

Catatan: file `.form` dibuat dengan alat bantu, bukan oleh NetBeans sendiri. Bila NetBeans gagal
membuka tab Design, program tetap berjalan normal dari file `.java`.

## Struktur class (package `mynaspad`)

| Class | Peran | Konsep PBO |
| --- | --- | --- |
| `KetersediaanMenu` | Kontrak stok: `stokMasuk`, `stokKeluar`, `isTersedia` | Interface |
| `MenuNaspad` | Superclass menu, atribut private | Abstract class, encapsulation |
| `LaukPauk`, `Minuman`, `Pelengkap` | Jenis menu: kategori, satuan, info, dan harga masing-masing | Inheritance, polymorphism |
| `ItemPesanan`, `Pesanan` | Keranjang dan perhitungan total, kembalian, struk | Encapsulation |
| `MenuDAO`, `PesananDAO`, `Koneksi` | Akses database MySQL | |
| `FormUtama`, `FormPesan` | Tampilan JFrame | Inheritance (extends JFrame) |
| `FormatRupiah`, `MyNaspad` | Bantu format Rp, main | |

## Isi folder

- `src/main/java/mynaspad/` kode program dan file `.form`
- `database/db_my_naspad.sql` skrip database dan data contoh
- `docs/` class diagram (PNG dan sumber `.dot`) dan slide presentasi
