# Identitas

- **Nama**: I Made Priyandika Adiyatma Putra Sari
- **NIM**: F1D02410008

# SKS: Sistem Kasir Sederhana
Aplikasi Kasir Kafe / Kantin Kampus Berbasis Java Swing (GUI)

---

## Deskripsi Singkat
SKS (Sistem Kasir Sederhana) adalah aplikasi desktop kasir yang dirancang untuk mempermudah pencatatan pesanan menu di kafe atau kantin kampus. Aplikasi ini memiliki antarmuka dua bagian: form input pesanan di sisi kiri dan struk digital di sisi kanan.

Proyek ini dibangun untuk memenuhi kriteria eksplorasi Graphical User Interface (GUI) pada mata kuliah Pemrograman Berorientasi Objek (PBO).

---

## Pemenuhan 5 Kriteria Komponen Utama GUI

### 1. Top-Level Container Class
* **JFrame**: Digunakan sebagai bingkai jendela utama aplikasi (`extends JFrame`) dengan ukuran 780x500 piksel, posisi di tengah layar (`setLocationRelativeTo(null)`), dan penghentian proses saat ditutup (`EXIT_ON_CLOSE`).

### 2. Intermediate Container Class
* **JPanel**: Digunakan untuk mengelompokkan elemen antarmuka:
  * `panelForm`: Panel form input di sebelah kiri.
  * `panelKanan`: Panel struk di sebelah kanan.
  * `panelTipe`: Panel penampung radio button tipe pesanan.
  * `panelEkstra`: Panel penampung checkbox opsi tambahan.
  * `panelBayar`: Panel ringkasan total dan tombol bayar.
* **JScrollPane**: Membungkus area teks struk (`JTextArea`) agar isi struk dapat digulir jika pesanan bertambah banyak.

### 3. Atomic Component Class
* **JLabel**: Menampilkan teks statis pembimbing form, label menu, dan teks total bayar.
* **JTextField**:
  * `txtNama`: Input teks nama pelanggan.
  * `txtJumlah`: Input kuantitas pesanan.
* **JComboBox**: Menu dropdown untuk memilih daftar menu makanan/minuman beserta harganya.
* **JRadioButton & ButtonGroup**: Pilihan eksklusif tipe pesanan (Dine-in vs Takeaway).
* **JCheckBox**: Pilihan opsi tambahan yang bisa dipilih lebih dari satu (Ekstra **Shot Espresso** dan Ekstra **Topping**).
* **JButton**: Tombol aksi interaktif (Tambah Pesanan, Reset Form, dan Bayar / Selesai).
* **JTextArea**: Menampilkan lembar struk kasir digital dengan font monospace `Consolas`.
* **JOptionPane**: Menampilkan kotak dialog validasi peringatan dan dialog konfirmasi transaksi.

### 4. Layout Classes
Aplikasi ini mengimplementasikan 3 jenis layout manager:
1. **BorderLayout**: Diterapkan pada JFrame utama serta panel kanan struk.
2. **GridLayout**: Diterapkan pada panel form input (6 baris, 2 kolom) agar susunan label dan kolom input rapi sejajar.
3. **FlowLayout**: Diterapkan pada panel pilihan radio button dan checkbox tambahan.

### 5. Event Listener
Aplikasi mengimplementasikan 2 interface listener:
* **ActionListener**: Meng‑override method `actionPerformed` untuk menangani logika klik tombol Tambah, Reset, dan Bayar.
* **MouseListener**: Meng‑override method `mouseEntered` dan `mouseExited` untuk memberikan efek visual perubahan warna (hover) saat kursor mouse melintas di atas tombol.

---

## Cara Menjalankan Program

### 1. Kompilasi Kode:
```bash
javac -d bin src/SistemKasirSederjana.java
```

### 2. Jalankan Aplikasi:
```bash
java -cp bin SistemKasirSederjana
```

---

## Screenshot Aplikasi

### 1. Tampilan Awal Aplikasi
![Tampilan Awal](screenshot/01-Tampilan%20Awal.png)

### 2. Tambah Pesanan
![Tambah Pesanan](screenshot/02-Tambah%20Pesanan.png)

### 3. Selesai Pembayaran
![Selesai Pembayaran](screenshot/03-Struk%20Selesai%20Pembayaran.png)
