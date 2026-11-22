CREATE DATABASE IF NOT EXISTS db_my_naspad;
USE db_my_naspad;

-- Tabel menu menyimpan daftar menu (lauk pauk, minuman, pelengkap).
-- Data menu diisi langsung di database, bukan lewat aplikasi.
CREATE TABLE IF NOT EXISTS menu (
    kode_menu  VARCHAR(10)  PRIMARY KEY,
    nama_menu  VARCHAR(100) NOT NULL,
    kategori   VARCHAR(20)  NOT NULL,  
    harga      DOUBLE       NOT NULL,
    stok       INT          NOT NULL
);

-- Tabel pesanan: satu baris per transaksi
CREATE TABLE IF NOT EXISTS pesanan (
    id_pesanan     INT AUTO_INCREMENT PRIMARY KEY,
    nama_pelanggan VARCHAR(100) NOT NULL,
    tanggal        DATETIME     NOT NULL,
    total          DOUBLE       NOT NULL,
    bayar          DOUBLE       NOT NULL,
    kembalian      DOUBLE       NOT NULL
);

-- Tabel detail_pesanan: rincian menu pada setiap pesanan
CREATE TABLE IF NOT EXISTS detail_pesanan (
    id_detail  INT AUTO_INCREMENT PRIMARY KEY,
    id_pesanan INT         NOT NULL,
    kode_menu  VARCHAR(10) NOT NULL,
    jumlah     INT         NOT NULL,
    subtotal   DOUBLE      NOT NULL,
    FOREIGN KEY (id_pesanan) REFERENCES pesanan(id_pesanan),
    FOREIGN KEY (kode_menu)  REFERENCES menu(kode_menu)
);

-- Data awal 
INSERT INTO menu (kode_menu, nama_menu, kategori, harga, stok) VALUES
('LP01', 'Rendang Sapi',      'Lauk Pauk', 25000, 20),
('LP02', 'Ayam Pop',          'Lauk Pauk', 20000, 20),
('LP03', 'Gulai Ikan',        'Lauk Pauk', 22000, 15),
('LP04', 'Telur Balado',      'Lauk Pauk', 8000,  30),
('LP05', 'Dendeng Balado',    'Lauk Pauk', 27000, 10),
('MN01', 'Es Teh Manis',      'Minuman',   5000,  50),
('MN02', 'Teh Talua',         'Minuman',   12000, 20),
('MN03', 'Es Jeruk',          'Minuman',   7000,  40),
('PL01', 'Nasi Putih',        'Pelengkap', 5000,  100),
('PL02', 'Sambal Ijo Extra',  'Pelengkap', 3000,  60),
('PL03', 'Kerupuk Jangek',    'Pelengkap', 4000,  40);
