import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class SistemKasirSederhana extends JFrame implements ActionListener, MouseListener {

    // 1. Komponen Atomik: Input Form
    private JTextField txtNama;
    private JComboBox<String> cbMenu;
    private JRadioButton rbDineIn, rbTakeAway;
    private ButtonGroup bgTipe;
    private JCheckBox cbShot, cbTopping;
    private JTextField txtJumlah;

    // 2. Komponen Atomik: Tombol
    private JButton btnTambah, btnReset, btnBayar;

    // 3. Komponen Atomik: Struk & Label
    private JTextArea txtStruk;
    private JLabel lblTotal;

    // 4. Data Menu Sederhana
    private final String[] menuList = {
        "Kopi Susu - Rp 15000",
        "Americano - Rp 12000",
        "Teh Manis - Rp 6000",
        "Mie Goreng - Rp 14000",
        "Roti Bakar - Rp 13000"
    };
    private final int[] hargaList = {15000, 12000, 6000, 14000, 13000};
    private int totalBayar = 0;

    public SistemKasirSederhana() {
        // A. Konfigurasi Top-Level Container (JFrame)
        setTitle("SKS - Sistem Kasir Sederhana");
        setSize(780, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout Utama JFrame: BorderLayout
        getContentPane().setLayout(new BorderLayout(15, 15));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 12);

        // ==========================================
        // B. PANEL FORM (KIRI) - Intermediate: JPanel
        // Menggunakan GridLayout(6, 2)
        // ==========================================
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 12));
        panelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Form Pesanan", 
            TitledBorder.LEFT, TitledBorder.TOP, fontLabel
        ));

        // 1. Nama Pelanggan
        JLabel lblNama = new JLabel("Nama Pelanggan:");
        lblNama.setFont(fontLabel);
        txtNama = new JTextField();
        txtNama.setFont(fontInput);
        panelForm.add(lblNama);
        panelForm.add(txtNama);

        // 2. Menu
        JLabel lblMenu = new JLabel("Pilih Menu:");
        lblMenu.setFont(fontLabel);
        cbMenu = new JComboBox<>(menuList);
        cbMenu.setFont(fontInput);
        panelForm.add(lblMenu);
        panelForm.add(cbMenu);

        // 3. Tipe Pesanan (JRadioButton & FlowLayout)
        JLabel lblTipe = new JLabel("Tipe Pesanan:");
        lblTipe.setFont(fontLabel);
        JPanel panelTipe = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rbDineIn = new JRadioButton("Dine-in", true);
        rbTakeAway = new JRadioButton("Takeaway");
        rbDineIn.setFont(fontInput);
        rbTakeAway.setFont(fontInput);
        bgTipe = new ButtonGroup();
        bgTipe.add(rbDineIn);
        bgTipe.add(rbTakeAway);
        panelTipe.add(rbDineIn);
        panelTipe.add(rbTakeAway);
        panelForm.add(lblTipe);
        panelForm.add(panelTipe);

        // 4. Tambahan / Ekstra (JCheckBox & FlowLayout)
        JLabel lblEkstra = new JLabel("Opsi Tambahan:");
        lblEkstra.setFont(fontLabel);
        JPanel panelEkstra = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        cbShot = new JCheckBox("Shot (+3000)");
        cbTopping = new JCheckBox("Topping (+4000)");
        cbShot.setFont(fontInput);
        cbTopping.setFont(fontInput);
        panelEkstra.add(cbShot);
        panelEkstra.add(cbTopping);
        panelForm.add(lblEkstra);
        panelForm.add(panelEkstra);

        // 5. Jumlah
        JLabel lblJumlah = new JLabel("Jumlah Porsi:");
        lblJumlah.setFont(fontLabel);
        txtJumlah = new JTextField("1");
        txtJumlah.setFont(fontInput);
        panelForm.add(lblJumlah);
        panelForm.add(txtJumlah);

        // 6. Tombol Form
        btnTambah = new JButton("Tambah Pesanan");
        btnReset = new JButton("Reset Form");
        btnTambah.setFont(fontLabel);
        btnReset.setFont(fontLabel);
        btnTambah.setForeground(Color.BLACK);
        btnReset.setForeground(Color.BLACK);
        btnTambah.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTambah.addActionListener(this);
        btnTambah.addMouseListener(this);
        btnReset.addActionListener(this);
        btnReset.addMouseListener(this);
        panelForm.add(btnTambah);
        panelForm.add(btnReset);

        getContentPane().add(panelForm, BorderLayout.WEST);

        // ==========================================
        // C. PANEL KANAN (STRUK & BAYAR)
        // Menggunakan JScrollPane dan JTextArea
        // ==========================================
        JPanel panelKanan = new JPanel(new BorderLayout(10, 10));
        panelKanan.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), "Struk Kasir",
            TitledBorder.LEFT, TitledBorder.TOP, fontLabel
        ));

        txtStruk = new JTextArea();
        txtStruk.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtStruk.setEditable(false);
        resetTampilanStruk();

        JScrollPane scrollStruk = new JScrollPane(txtStruk);
        panelKanan.add(scrollStruk, BorderLayout.CENTER);

        // Bagian Bawah Struk (Total & Tombol Bayar)
        JPanel panelBayar = new JPanel(new BorderLayout(10, 0));
        lblTotal = new JLabel("Total: Rp 0");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));

        btnBayar = new JButton("Bayar / Selesai");
        btnBayar.setFont(fontLabel);
        btnBayar.setForeground(Color.BLACK);
        btnBayar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBayar.addActionListener(this);
        btnBayar.addMouseListener(this);

        panelBayar.add(lblTotal, BorderLayout.WEST);
        panelBayar.add(btnBayar, BorderLayout.EAST);
        panelKanan.add(panelBayar, BorderLayout.SOUTH);

        getContentPane().add(panelKanan, BorderLayout.CENTER);
    }

    private void resetTampilanStruk() {
        txtStruk.setText("");
        txtStruk.append("========================================\n");
        txtStruk.append("       SKS - SISTEM KASIR SEDERHANA     \n");
        txtStruk.append("========================================\n");
        txtStruk.append("Pesanan:\n");
        txtStruk.append("----------------------------------------\n");
    }

    // ==========================================
    // D. EVENT HANDLING: ACTIONLISTENER
    // ==========================================
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnTambah) {
            String nama = txtNama.getText().trim();
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama pelanggan tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int jumlah = 0;
            try {
                jumlah = Integer.parseInt(txtJumlah.getText().trim());
                if (jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka bulat!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idxMenu = cbMenu.getSelectedIndex();
            int hargaSatuan = hargaList[idxMenu];
            String namaMenu = menuList[idxMenu].split(" - ")[0];

            int biayaEkstra = 0;
            String teksEkstra = "";
            if (cbShot.isSelected()) {
                biayaEkstra += 3000;
                teksEkstra += " +Shot";
            }
            if (cbTopping.isSelected()) {
                biayaEkstra += 4000;
                teksEkstra += " +Topping";
            }

            String tipe = rbDineIn.isSelected() ? "Dine-in" : "Takeaway";
            int subtotal = (hargaSatuan + biayaEkstra) * jumlah;
            totalBayar += subtotal;

            txtStruk.append(namaMenu + teksEkstra + " (" + tipe + ")\n");
            txtStruk.append("  " + jumlah + " x Rp " + (hargaSatuan + biayaEkstra) + " = Rp " + subtotal + "\n");

            lblTotal.setText("Total: Rp " + totalBayar);
            txtJumlah.setText("1");

        } else if (e.getSource() == btnReset) {
            txtNama.setText("");
            cbMenu.setSelectedIndex(0);
            rbDineIn.setSelected(true);
            cbShot.setSelected(false);
            cbTopping.setSelected(false);
            txtJumlah.setText("1");

        } else if (e.getSource() == btnBayar) {
            if (totalBayar == 0) {
                JOptionPane.showMessageDialog(this, "Belum ada pesanan yang ditambahkan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nama = txtNama.getText().trim();
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Nama: " + nama + "\nTotal Bayar: Rp " + totalBayar + "\nLanjutkan transaksi?",
                "Konfirmasi Pembayaran", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                txtStruk.append("----------------------------------------\n");
                txtStruk.append("Pelanggan : " + nama + "\n");
                txtStruk.append("TOTAL     : Rp " + totalBayar + "\n");
                txtStruk.append("STATUS    : LUNAS\n");
                txtStruk.append("========================================\n");

                JOptionPane.showMessageDialog(this, "Transaksi selesai dan lunas!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                totalBayar = 0;
                lblTotal.setText("Total: Rp 0");
                txtNama.setText("");
                txtJumlah.setText("1");
                cbShot.setSelected(false);
                cbTopping.setSelected(false);
            }
        }
    }

    // ==========================================
    // E. EVENT HANDLING: MOUSELISTENER (Hover)
    // ==========================================
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == btnTambah) {
            btnTambah.setForeground(new Color(0, 102, 204)); // Biru saat disorot kursor
        } else if (e.getSource() == btnReset) {
            btnReset.setForeground(new Color(220, 53, 69)); // Merah saat disorot kursor
        } else if (e.getSource() == btnBayar) {
            btnBayar.setForeground(new Color(40, 167, 69)); // Hijau saat disorot kursor
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == btnTambah) {
            btnTambah.setForeground(Color.BLACK);
        } else if (e.getSource() == btnReset) {
            btnReset.setForeground(Color.BLACK);
        } else if (e.getSource() == btnBayar) {
            btnBayar.setForeground(Color.BLACK);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    // ==========================================
    // F. MAIN METHOD & THREAD SAFETY
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
