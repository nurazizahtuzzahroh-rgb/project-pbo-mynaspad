package mynaspad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/** Form INPUT data menu (seperti FormPena pada modul) + tabel daftar menu. */
public class FormMenu extends JFrame {

    private final MenuDAO menuDAO = new MenuDAO();

    private JLabel lblJudul, lblKode, lblNama, lblKategori, lblHarga, lblStok;
    private JTextField txtKode, txtNama, txtHarga, txtStok;
    private JComboBox<String> cmbKategori;
    private JButton btnSubmit, btnTambahStok, btnHapus;
    private JTable tblMenu;
    private DefaultTableModel modelMenu;

    public FormMenu() {
        initComponents();
        muatTabel();
    }

    private void initComponents() {
        setTitle("My Naspad - Input Menu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        lblJudul = new JLabel("INPUT DATA MENU", SwingConstants.CENTER);
        lblJudul.setFont(lblJudul.getFont().deriveFont(Font.BOLD, 20f));

        lblKode = new JLabel("Kode Menu");
        lblNama = new JLabel("Nama Menu");
        lblKategori = new JLabel("Kategori");
        lblHarga = new JLabel("Harga");
        lblStok = new JLabel("Stok");
        txtKode = new JTextField(10);
        txtNama = new JTextField(20);
        txtHarga = new JTextField(10);
        txtStok = new JTextField(10);
        cmbKategori = new JComboBox<>(MenuDAO.DAFTAR_KATEGORI);

        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(BorderFactory.createTitledBorder("Data Menu Baru"));
        tambahBaris(pnlForm, 0, lblKode, txtKode);
        tambahBaris(pnlForm, 1, lblNama, txtNama);
        tambahBaris(pnlForm, 2, lblKategori, cmbKategori);
        tambahBaris(pnlForm, 3, lblHarga, txtHarga);
        tambahBaris(pnlForm, 4, lblStok, txtStok);

        btnSubmit = new JButton("SUBMIT");
        btnTambahStok = new JButton("TAMBAH STOK");
        btnHapus = new JButton("HAPUS");
        btnSubmit.addActionListener(evt -> btnSubmitActionPerformed());
        btnTambahStok.addActionListener(evt -> btnTambahStokActionPerformed());
        btnHapus.addActionListener(evt -> btnHapusActionPerformed());

        JPanel pnlTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlTombol.add(btnSubmit);

        JPanel pnlAtas = new JPanel(new BorderLayout(5, 5));
        pnlAtas.add(lblJudul, BorderLayout.NORTH);
        pnlAtas.add(pnlForm, BorderLayout.CENTER);
        pnlAtas.add(pnlTombol, BorderLayout.SOUTH);
        add(pnlAtas, BorderLayout.NORTH);

        modelMenu = new DefaultTableModel(
                new String[]{"Kode", "Nama Menu", "Kategori", "Harga", "Stok", "Promo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblMenu = new JTable(modelMenu);
        tblMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblMenu.getColumnModel().getColumn(5).setPreferredWidth(260);
        JScrollPane scroll = new JScrollPane(tblMenu);
        scroll.setPreferredSize(new Dimension(720, 240));
        scroll.setBorder(BorderFactory.createTitledBorder("Daftar Menu"));
        add(scroll, BorderLayout.CENTER);

        JPanel pnlBawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlBawah.add(btnTambahStok);
        pnlBawah.add(btnHapus);
        add(pnlBawah, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void tambahBaris(JPanel panel, int baris, JLabel label, JComponent isian) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 6, 4, 6);
        c.gridy = baris;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        panel.add(label, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        panel.add(isian, c);
    }

    /** Menampilkan seluruh menu dari database ke tabel. */
    private void muatTabel() {
        try {
            List<MenuNaspad> daftar = menuDAO.getSemuaMenu();
            modelMenu.setRowCount(0);
            for (MenuNaspad m : daftar) {
                modelMenu.addRow(new Object[]{
                    m.getKodeMenu(), m.getNamaMenu(), m.getKategori(),
                    FormatRupiah.format(m.getHarga()), m.getStok(), m.getInfoPromo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data menu: " + e.getMessage());
        }
    }

    private void btnSubmitActionPerformed() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String hargaText = txtHarga.getText().trim();
        String stokText = txtStok.getText().trim();
        String kategori = (String) cmbKategori.getSelectedItem();

        if (kode.isEmpty() || nama.isEmpty() || hargaText.isEmpty() || stokText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
            return;
        }
        if (kode.length() > 10) {
            JOptionPane.showMessageDialog(this, "Kode menu maksimal 10 karakter!");
            return;
        }

        try {
            double harga = Double.parseDouble(hargaText);
            int stok = Integer.parseInt(stokText);

            // Membuat objek sesuai kategori (LaukPauk / Minuman / Pelengkap)
            MenuNaspad menu = MenuDAO.buatMenu(kategori, kode, nama, harga, stok);
            menuDAO.simpan(menu);

            JOptionPane.showMessageDialog(this, "Data menu berhasil disimpan!");
            txtKode.setText("");
            txtNama.setText("");
            txtHarga.setText("");
            txtStok.setText("");
            txtKode.requestFocus();
            muatTabel();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(this, "Kode menu sudah dipakai!");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
            }
        }
    }

    /** Stok masuk: memakai method stokMasuk() dari interface KetersediaanMenu. */
    private void btnTambahStokActionPerformed() {
        int baris = tblMenu.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih menu pada tabel terlebih dahulu!");
            return;
        }
        String kode = (String) modelMenu.getValueAt(baris, 0);
        String input = JOptionPane.showInputDialog(this, "Jumlah stok masuk untuk " + kode + ":");
        if (input == null) {
            return;
        }
        try {
            int jumlah = Integer.parseInt(input.trim());
            MenuNaspad menu = menuDAO.getByKode(kode);
            if (menu == null) {
                JOptionPane.showMessageDialog(this, "Menu tidak ditemukan.");
                return;
            }
            menu.stokMasuk(jumlah);
            menuDAO.updateStok(kode, menu.getStok());
            muatTabel();
            JOptionPane.showMessageDialog(this, "Stok berhasil ditambah. Stok sekarang: " + menu.getStok());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui stok: " + e.getMessage());
        }
    }

    private void btnHapusActionPerformed() {
        int baris = tblMenu.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih menu pada tabel terlebih dahulu!");
            return;
        }
        String kode = (String) modelMenu.getValueAt(baris, 0);
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Hapus menu " + kode + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            menuDAO.hapus(kode);
            muatTabel();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Menu tidak bisa dihapus (kemungkinan sudah pernah dipesan).\n" + e.getMessage());
        }
    }
}
