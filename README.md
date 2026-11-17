# SKS: Sistem Kasir Sederhana ☕🎓
> Aplikasi Point of Sale (POS) Kasir Kafe / Kantin Kampus Berbasis Java Swing (GUI)

---

## 📌 Deskripsi Singkat
**SKS (Sistem Kasir Sederhana)** adalah aplikasi desktop kasir praktis yang dirancang khusus untuk mempermudah pencatatan pesanan di kafe, kantin, atau tempat nongkrong mahasiswa. Aplikasi ini menyediakan antarmuka dua panel yang intuitif: panel formulir input pesanan dinamis di sisi kiri dan panel struk transaksi digital real-time di sisi kanan.

Proyek ini dibangun sebagai bentuk **eksplorasi mendalam materi Graphical User Interface (GUI)** pada mata kuliah Pemrograman Berorientasi Objek (PBO) semester 5.

---

## 🏛️ Pemenuhan 5 Kriteria Komponen Utama GUI

Aplikasi ini mengimplementasikan seluruh kriteria komponen GUI Java Swing secara lengkap:

### 1. Top-Level Container Class
* **`JFrame`**: Digunakan sebagai jendela utama aplikasi (`extends JFrame`) dengan ukuran 850x600 piksel, posisi di tengah layar (`setLocationRelativeTo(null)`), dan penghentian proses total saat jendela ditutup (`EXIT_ON_CLOSE`).

### 2. Intermediate Container Class
* **`JPanel`**: Digunakan untuk mengelompokkan area antarmuka:
  * `panelHeader`: Banner judul aplikasi di area atas.
  * `panelForm`: Wadah formulir pemesanan di kolom kiri.
  * `panelStruk`: Wadah pratinjau struk di kolom kanan.
  * `panelBawah`: Wadah ringkasan total tagihan dan tombol aksi pembayaran.
  * Sub-panel pengelompokan kecil untuk radio button dan checkbox.
* **`JScrollPane`**: Membungkus area teks struk (`JTextArea`) agar struk transaksi dapat digulir (*scrollable*) secara mulus jika pesanan bertambah banyak.

### 3. Atomic Component Class
* **`JLabel`**: Menampilkan teks keterangan menu, panduan pengisian, header, dan angka total tagihan.
* **`JTextField`**: 
  * `txtNamaPelanggan`: Input teks nama pemesan.
  * `txtJumlah`: Input angka kuantitas porsi pesanan.
* **`JComboBox<String>`**: Menu *dropdown* interaktif berisi daftar menu minuman dan makanan beserta harga satuannya.
* **`JRadioButton` & `ButtonGroup`**: Pilihan eksklusif tipe pemesanan (*Dine-in / Makan di Tempat* vs *Takeaway / Bungkus*).
* **`JCheckBox`**: Pilihan opsi ekstra mandiri yang dapat dipilih lebih dari satu (*Ekstra Shot/Keju* +Rp 3.000 dan *Ekstra Boba/Telur* +Rp 4.000).
* **`JButton`**: Tombol interaktif untuk memicu aksi:
  * `btnTambah`: Menambahkan item ke daftar struk.
  * `btnResetForm`: Mereset inputan form.
  * `btnResetSemua`: Membatalkan seluruh transaksi yang sedang berlangsung.
  * `btnBayar`: Menyelesaikan pesanan dan mencetak struk lunas.
* **`JTextArea`**: Menampilkan struk kasir digital dengan font monospace `Consolas` berformat rapi.
* **`JOptionPane`**: Menampilkan pop-up dialog peringatan validasi input kosong/angka (`showMessageDialog`) serta dialog konfirmasi sebelum pembayaran (`showConfirmDialog`).

### 4. Layout Classes (Implementasi 3 Layout Sekaligus)
Aplikasi ini mengeksplorasi **3 jenis layout manager**:
1. **`BorderLayout`**: Diterapkan pada `JFrame` utama (membagi area `NORTH`, `CENTER`, dan `SOUTH`) serta panel struk dan header.
2. **`GridLayout`**: Diterapkan pada panel tengah (membagi layar menjadi 2 kolom seimbang: 1 baris, 2 kolom) dan pada kelompok form isian agar komponen berjejer rapi.
3. **`FlowLayout`**: Diterapkan pada penataan horizontal tombol radio tipe pesanan, opsi checkbox, serta jajaran tombol aksi di panel bawah.

### 5. Event Listener (Method Override)
Mengimplementasikan dua antarmuka listener (*Multiple Interface Implementation*):
* **`ActionListener`**: Meng-override method `actionPerformed(ActionEvent e)` untuk mengolah logika bisnis:
  * Mengambil data input, memvalidasi agar nama tidak kosong dan kuantitas berupa angka positif.
  * Mengkalkulasi subtotal harga (harga dasar menu + biaya ekstra × kuantitas).
  * Mencetak baris pesanan ke dalam `JTextArea` struk.
  * Memproses pembayaran dan menutup struk dengan status *LUNAS (PAID)*.
* **`MouseListener`**: Meng-override method `mouseEntered` dan `mouseExited` untuk memberikan efek visual animasi *hover* (perubahan warna latar belakang tombol saat kursor melintas). Method `mouseClicked`, `mousePressed`, dan `mouseReleased` tetap di-override sebagai bentuk pemenuhan kontrak interface.

---

## ⚡ Thread Safety & Eksekusi Program
Aplikasi ini mematuhi standar *Thread Safety* Java Swing:
```java
SwingUtilities.invokeLater(() -> {
    new SistemKasirSederhana().setVisible(true);
});
```
Inisialisasi komponen GUI didelegasikan ke **Event Dispatch Thread (EDT)** menggunakan objek `Runnable` (ekspresi lambda) agar rendering antarmuka berjalan stabil tanpa membebani *Main Thread*.

---

## 📸 Screenshot Tampilan Aplikasi

### 1. Tampilan Awal Aplikasi
Tampilan antarmuka saat aplikasi pertama kali dibuka, formulir siap diisi dan struk digital dalam kondisi bersih:
![Tampilan Awal](screenshot/01-Tampilan%20Awal.png)

### 2. Input dan Penambahan Pesanan
Simulasi penambahan beberapa menu pesanan dengan opsi ekstra, kalkulasi kuantitas, dan pencatatan struk secara dinamis:
![Tambah Pesanan](screenshot/02-Tambah%20Pesanan.png)

### 3. Selesai Transaksi & Struk Lunas
Struk transaksi digital setelah proses konfirmasi pembayaran berhasil diselesaikan:
![Struk Selesai](screenshot/03-Struk%20Selesai%20Pembayaran.png)

---

## 🚀 Panduan Menjalankan Program

### Kompilasi Kode:
```bash
javac -d bin src/SistemKasirSederhana.java
```

### Menjalankan Program:
```bash
java -cp bin SistemKasirSederhana
```
