import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SistemKasirSederhana extends JFrame implements ActionListener, MouseListener {

    // 1. Komponen Atomik: Form Input
    private JTextField txtNamaPelanggan;
    private JComboBox<String> cbMenu;
    private JRadioButton rbDineIn, rbTakeAway;
    private ButtonGroup bgTipePesanan;
    private JCheckBox cbEkstraShot, cbEkstraTopping;
    private JTextField txtJumlah;

    // 2. Komponen Atomik: Tombol Interaktif
    private JButton btnTambah, btnResetForm, btnBayar, btnResetSemua;

    // 3. Komponen Atomik: Penampil Struk & Total
    private JTextArea txtStruk;
    private JLabel lblTotalBayar;

    // 4. Data Bisnis Sederhana
    private int totalBelanja = 0;
    private int counterItem = 0;
    private NumberFormat kursRupiah;

    // Data Daftar Menu dan Harga
    private final String[] daftarMenu = {
        "Kopi Susu Kampus - Rp 15.000",
        "Americano Skripsi - Rp 12.000",
        "Matcha Green Tea - Rp 18.000",
        "Mie Dok-dok Mahasiswa - Rp 14.000",
        "Roti Bakar Coklat Keju - Rp 13.000",
        "Es Teh Manis Jumbo - Rp 6.000"
    };

    private final int[] hargaMenu = {15000, 12000, 18000, 14000, 13000, 6000};
    private final int HARGA_EKSTRA_SHOT = 3000;
    private final int HARGA_EKSTRA_TOPPING = 4000;

    public SistemKasirSederhana() {
        // A. Konfigurasi Top-Level Container (JFrame)
        setTitle("SKS: Sistem Kasir Sederhana ☕🎓");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Posisi tengah layar

        kursRupiah = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));

        // Mengatur Layout Utama JFrame: BorderLayout
        getContentPane().setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(248, 250, 252));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 13);

        // ==========================================
        // B. HEADER BANNER (BorderLayout.NORTH)
        // ==========================================
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(new Color(30, 41, 59)); // Slate dark
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblJudul = new JLabel("SKS: Sistem Kasir Sederhana ☕");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);

        JLabel lblSubJudul = new JLabel("Aplikasi Kasir Praktis F&B / Kafe Kampus • PBO GUI Exploration");
        lblSubJudul.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubJudul.setForeground(new Color(148, 163, 184));

        panelHeader.add(lblJudul, BorderLayout.NORTH);
        panelHeader.add(lblSubJudul, BorderLayout.SOUTH);
        getContentPane().add(panelHeader, BorderLayout.NORTH);

        // ==========================================
        // C. KONTEN TENGAH (BorderLayout.CENTER)
        // Dibagi 2 Kolom menggunakan GridLayout(1, 2, 15, 15)
        // ==========================================
        JPanel panelTengah = new JPanel(new GridLayout(1, 2, 15, 15));
        panelTengah.setBackground(new Color(248, 250, 252));
        panelTengah.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // ------------------------------------------
        // C1. PANEL FORM INPUT (KOLOM KIRI)
        // Menggunakan Intermediate: JPanel dengan GridLayout
        // ------------------------------------------
        JPanel panelForm = new JPanel(new BorderLayout(10, 10));
        panelForm.setBackground(Color.WHITE);
        TitledBorder borderForm = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            " 📝 Formulir Input Pesanan "
        );
        borderForm.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        borderForm.setTitleColor(new Color(30, 41, 59));
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            borderForm,
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Form Isian (GridLayout 5 baris, 1 kolom untuk kelompok input)
        JPanel panelIsian = new JPanel(new GridLayout(5, 1, 8, 8));
        panelIsian.setBackground(Color.WHITE);

        // 1. Nama Pelanggan
        JPanel groupNama = new JPanel(new BorderLayout(5, 2));
        groupNama.setBackground(Color.WHITE);
        JLabel lblNama = new JLabel("Nama Pelanggan:");
        lblNama.setFont(fontLabel);
        txtNamaPelanggan = new JTextField();
        txtNamaPelanggan.setFont(fontInput);
        groupNama.add(lblNama, BorderLayout.NORTH);
        groupNama.add(txtNamaPelanggan, BorderLayout.CENTER);
        panelIsian.add(groupNama);

        // 2. Menu Pilihan (JComboBox)
        JPanel groupMenu = new JPanel(new BorderLayout(5, 2));
        groupMenu.setBackground(Color.WHITE);
        JLabel lblMenu = new JLabel("Pilih Menu:");
        lblMenu.setFont(fontLabel);
        cbMenu = new JComboBox<>(daftarMenu);
        cbMenu.setFont(fontInput);
        cbMenu.setBackground(Color.WHITE);
        groupMenu.add(lblMenu, BorderLayout.NORTH);
        groupMenu.add(cbMenu, BorderLayout.CENTER);
        panelIsian.add(groupMenu);

        // 3. Tipe Pesanan (JRadioButton & FlowLayout)
        JPanel groupTipe = new JPanel(new BorderLayout(5, 2));
        groupTipe.setBackground(Color.WHITE);
        JLabel lblTipe = new JLabel("Tipe Pesanan:");
        lblTipe.setFont(fontLabel);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRadio.setBackground(Color.WHITE);
        rbDineIn = new JRadioButton("Dine-in (Makan di Tempat)", true);
        rbTakeAway = new JRadioButton("Takeaway (Bungkus)");
        rbDineIn.setFont(fontInput);
        rbTakeAway.setFont(fontInput);
        rbDineIn.setBackground(Color.WHITE);
        rbTakeAway.setBackground(Color.WHITE);

        bgTipePesanan = new ButtonGroup();
        bgTipePesanan.add(rbDineIn);
        bgTipePesanan.add(rbTakeAway);

        panelRadio.add(rbDineIn);
        panelRadio.add(rbTakeAway);
        groupTipe.add(lblTipe, BorderLayout.NORTH);
        groupTipe.add(panelRadio, BorderLayout.CENTER);
        panelIsian.add(groupTipe);

        // 4. Opsi Ekstra (JCheckBox & FlowLayout)
        JPanel groupEkstra = new JPanel(new BorderLayout(5, 2));
        groupEkstra.setBackground(Color.WHITE);
        JLabel lblEkstra = new JLabel("Pilihan Tambahan (Opsi Ekstra):");
        lblEkstra.setFont(fontLabel);

        JPanel panelCheck = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelCheck.setBackground(Color.WHITE);
        cbEkstraShot = new JCheckBox("Ekstra Shot/Keju (+Rp 3.000)");
        cbEkstraTopping = new JCheckBox("Ekstra Boba/Telur (+Rp 4.000)");
        cbEkstraShot.setFont(fontInput);
        cbEkstraTopping.setFont(fontInput);
        cbEkstraShot.setBackground(Color.WHITE);
        cbEkstraTopping.setBackground(Color.WHITE);

        panelCheck.add(cbEkstraShot);
        panelCheck.add(cbEkstraTopping);
        groupEkstra.add(lblEkstra, BorderLayout.NORTH);
        groupEkstra.add(panelCheck, BorderLayout.CENTER);
        panelIsian.add(groupEkstra);

        // 5. Jumlah Porsi (JTextField)
        JPanel groupJumlah = new JPanel(new BorderLayout(5, 2));
        groupJumlah.setBackground(Color.WHITE);
        JLabel lblJumlah = new JLabel("Jumlah Porsi (Kuantitas):");
        lblJumlah.setFont(fontLabel);
        txtJumlah = new JTextField("1");
        txtJumlah.setFont(fontInput);
        groupJumlah.add(lblJumlah, BorderLayout.NORTH);
        groupJumlah.add(txtJumlah, BorderLayout.CENTER);
        panelIsian.add(groupJumlah);

        panelForm.add(panelIsian, BorderLayout.CENTER);

        // Tombol Aksi Form Input
        JPanel panelBtnForm = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBtnForm.setBackground(Color.WHITE);
        panelBtnForm.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        btnTambah = buatTombol("Tambah Item [+]", new Color(37, 99, 235)); // Biru
        btnResetForm = buatTombol("Batal / Reset Input", new Color(100, 116, 139)); // Slate Gray

        panelBtnForm.add(btnTambah);
        panelBtnForm.add(btnResetForm);
        panelForm.add(panelBtnForm, BorderLayout.SOUTH);

        // ------------------------------------------
        // C2. PANEL STRUK DIGITAL (KOLOM KANAN)
        // Menggunakan JScrollPane dan JTextArea
        // ------------------------------------------
        JPanel panelStruk = new JPanel(new BorderLayout(10, 10));
        panelStruk.setBackground(Color.WHITE);
        TitledBorder borderStruk = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
            " 🧾 Struk Transaksi Digital "
        );
        borderStruk.setTitleFont(new Font("Segoe UI", Font.BOLD, 13));
        borderStruk.setTitleColor(new Color(30, 41, 59));
        panelStruk.setBorder(BorderFactory.createCompoundBorder(
            borderStruk,
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        txtStruk = new JTextArea();
        txtStruk.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtStruk.setEditable(false);
        txtStruk.setBackground(new Color(248, 250, 252));
        txtStruk.setMargin(new Insets(10, 10, 10, 10));

        inisialisasiHeaderStruk();

        JScrollPane scrollStruk = new JScrollPane(txtStruk);
        scrollStruk.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));
        panelStruk.add(scrollStruk, BorderLayout.CENTER);

        // Masukkan Kolom Kiri & Kolom Kanan ke Panel Tengah
        panelTengah.add(panelForm);
        panelTengah.add(panelStruk);
        getContentPane().add(panelTengah, BorderLayout.CENTER);

        // ==========================================
        // D. PANEL TOTAL & BAYAR (BorderLayout.SOUTH)
        // ==========================================
        JPanel panelBawah = new JPanel(new BorderLayout(15, 0));
        panelBawah.setBackground(new Color(30, 41, 59));
        panelBawah.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblTotalBayar = new JLabel("Total Tagihan: Rp 0");
        lblTotalBayar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalBayar.setForeground(new Color(248, 250, 252));
        panelBawah.add(lblTotalBayar, BorderLayout.WEST);

        JPanel panelAksiBayar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAksiBayar.setBackground(new Color(30, 41, 59));

        btnResetSemua = buatTombol("Bersihkan Transaksi", new Color(225, 29, 72)); // Rose red
        btnBayar = buatTombol("Selesaikan Transaksi & Bayar [✓]", new Color(16, 185, 129)); // Hijau Emerald

        panelAksiBayar.add(btnResetSemua);
        panelAksiBayar.add(btnBayar);
        panelBawah.add(panelAksiBayar, BorderLayout.EAST);

        getContentPane().add(panelBawah, BorderLayout.SOUTH);
    }

    // Helper method untuk membuat tombol modern
    private JButton buatTombol(String teks, Color bg) {
        JButton btn = new JButton(teks);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.addActionListener(this);
        btn.addMouseListener(this);
        return btn;
    }

    private void inisialisasiHeaderStruk() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String waktuSekarang = sdf.format(new Date());

        txtStruk.setText("");
        txtStruk.append("==============================================\n");
        txtStruk.append("          SKS: SISTEM KASIR SEDERHANA         \n");
        txtStruk.append("       Tempat Ngopi & Santai Mahasiswa 🎓     \n");
        txtStruk.append("==============================================\n");
        txtStruk.append("Waktu Transaksi : " + waktuSekarang + "\n");
        txtStruk.append("Kasir           : Mahasiswa PBO Semester 5\n");
        txtStruk.append("----------------------------------------------\n");
        txtStruk.append(String.format("%-22s %4s %16s\n", "Item Pesanan", "Qty", "Subtotal"));
        txtStruk.append("----------------------------------------------\n");
    }

    // ==========================================
    // E. EVENT HANDLING: ACTIONLISTENER
    // ==========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            tambahPesanan();
        } else if (e.getSource() == btnResetForm) {
            resetInputForm();
        } else if (e.getSource() == btnResetSemua) {
            resetSeluruhTransaksi();
        } else if (e.getSource() == btnBayar) {
            prosesPembayaran();
        }
    }

    private void tambahPesanan() {
        String nama = txtNamaPelanggan.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nama pelanggan wajib diisi sebelum memesan!", 
                "Validasi Input", 
                JOptionPane.WARNING_MESSAGE);
            txtNamaPelanggan.requestFocus();
            return;
        }

        int jumlah = 0;
        try {
            jumlah = Integer.parseInt(txtJumlah.getText().trim());
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah porsi harus lebih dari 0!", 
                    "Peringatan Kuantitas", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Jumlah porsi harus berupa angka bulat positif!", 
                "Error Format Angka", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ambil data menu terpilih
        int indexMenu = cbMenu.getSelectedIndex();
        String namaMenu = daftarMenu[indexMenu].split(" - ")[0];
        int hargaSatuan = hargaMenu[indexMenu];

        // Hitung ekstra tambahan
        int biayaEkstra = 0;
        String labelEkstra = "";
        if (cbEkstraShot.isSelected()) {
            biayaEkstra += HARGA_EKSTRA_SHOT;
            labelEkstra += "+Shot ";
        }
        if (cbEkstraTopping.isSelected()) {
            biayaEkstra += HARGA_EKSTRA_TOPPING;
            labelEkstra += "+Topping ";
        }

        // Cek tipe pesanan
        String tipe = rbDineIn.isSelected() ? "Dine-in" : "Takeaway";

        // Perhitungan subtotal
        int subtotal = (hargaSatuan + biayaEkstra) * jumlah;
        totalBelanja += subtotal;
        counterItem++;

        // Jika ini item pertama, catat nama pelanggan di struk
        if (counterItem == 1) {
            txtStruk.insert("Pelanggan       : " + nama + " (" + tipe + ")\n", 
                txtStruk.getText().indexOf("----------------------------------------------\n"));
        }

        // Cetak item ke struk teks
        String infoMenu = namaMenu;
        if (!labelEkstra.isEmpty()) {
            infoMenu += " (" + labelEkstra.trim() + ")";
        }
        if (infoMenu.length() > 22) {
            infoMenu = infoMenu.substring(0, 19) + "...";
        }

        txtStruk.append(String.format("%-22s %4d  Rp %,12d\n", infoMenu, jumlah, subtotal));

        // Update tampilan label total
        lblTotalBayar.setText("Total Tagihan: " + kursRupiah.format(totalBelanja));

        // Kunci input nama agar satu pelanggan tidak gonta-ganti nama saat tambah pesanan
        txtNamaPelanggan.setEditable(false);

        // Reset kolom kuantitas dan ekstra untuk item berikutnya
        txtJumlah.setText("1");
        cbEkstraShot.setSelected(false);
        cbEkstraTopping.setSelected(false);
    }

    private void resetInputForm() {
        cbMenu.setSelectedIndex(0);
        rbDineIn.setSelected(true);
        cbEkstraShot.setSelected(false);
        cbEkstraTopping.setSelected(false);
        txtJumlah.setText("1");
    }

    private void resetSeluruhTransaksi() {
        if (counterItem == 0) {
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin membatalkan dan mereset seluruh transaksi?",
            "Konfirmasi Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            totalBelanja = 0;
            counterItem = 0;
            txtNamaPelanggan.setEditable(true);
            txtNamaPelanggan.setText("");
            resetInputForm();
            inisialisasiHeaderStruk();
            lblTotalBayar.setText("Total Tagihan: Rp 0");
        }
    }

    private void prosesPembayaran() {
        if (counterItem == 0 || totalBelanja == 0) {
            JOptionPane.showMessageDialog(this,
                "Belum ada pesanan yang dimasukkan ke struk!",
                "Transaksi Kosong",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nama = txtNamaPelanggan.getText().trim();
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Konfirmasi Pembayaran:\n" +
            "Nama Pelanggan : " + nama + "\n" +
            "Total Tagihan  : " + kursRupiah.format(totalBelanja) + "\n\n" +
            "Lanjutkan transaksi pembayaran?",
            "Proses Pembayaran",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Tutup struk dengan garis total akhir
            txtStruk.append("==============================================\n");
            txtStruk.append(String.format("%-26s  Rp %,12d\n", "TOTAL PEMBAYARAN:", totalBelanja));
            txtStruk.append("STATUS          : LUNAS (PAID) ✓\n");
            txtStruk.append("==============================================\n");
            txtStruk.append("      Terima Kasih atas Kunjungan Anda!       \n");
            txtStruk.append("            Semangat Skripsinya! ☕           \n");

            JOptionPane.showMessageDialog(this,
                "Transaksi Berhasil!\nPembayaran atas nama " + nama + " telah dicatat lunas.\nStruk digital telah dicetak.",
                "Sukses Pembayaran",
                JOptionPane.INFORMATION_MESSAGE);

            // Bersihkan form untuk transaksi berikutnya
            totalBelanja = 0;
            counterItem = 0;
            txtNamaPelanggan.setEditable(true);
            txtNamaPelanggan.setText("");
            resetInputForm();
            lblTotalBayar.setText("Total Tagihan: Rp 0");
        }
    }

    // ==========================================
    // F. EVENT HANDLING: MOUSELISTENER (Hover Animasi)
    // ==========================================
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == btnTambah) {
            btnTambah.setBackground(new Color(29, 78, 216)); // Biru lebih gelap
        } else if (e.getSource() == btnResetForm) {
            btnResetForm.setBackground(new Color(71, 85, 105));
        } else if (e.getSource() == btnResetSemua) {
            btnResetSemua.setBackground(new Color(190, 18, 60)); // Merah lebih gelap
        } else if (e.getSource() == btnBayar) {
            btnBayar.setBackground(new Color(5, 150, 105)); // Hijau lebih gelap
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == btnTambah) {
            btnTambah.setBackground(new Color(37, 99, 235));
        } else if (e.getSource() == btnResetForm) {
            btnResetForm.setBackground(new Color(100, 116, 139));
        } else if (e.getSource() == btnResetSemua) {
            btnResetSemua.setBackground(new Color(225, 29, 72));
        } else if (e.getSource() == btnBayar) {
            btnBayar.setBackground(new Color(16, 185, 129));
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    // ==========================================
    // G. MAIN METHOD & THREAD SAFETY (EDT)
    // ==========================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SistemKasirSederhana().setVisible(true);
        });
    }
}
