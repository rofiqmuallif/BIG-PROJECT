# 🏦 Sistem Informasi Koperasi Merah Putih (JavaFX)

Aplikasi Sistem Informasi Koperasi berbasis Desktop yang dibuat menggunakan **JavaFX**, menggunakan database **MySQL**, serta mengintegrasikan library **iTextPDF** untuk pencetakan laporan transaksi. Proyek ini dibuat untuk memenuhi tugas besar mata kuliah Pengembangan Sistem Informasi.

---

## 👥 Kelompok & Pembagian Tugas (Modul Utama)

Setiap anggota kelompok bertanggung jawab penuh atas modul utama masing-masing (termasuk file Model, DAO, View, dan Test di dalam foldernya):

| No | Nama Mahasiswa | NIM | Tanggung Jawab Modul | Detail Fitur Utama & Struktur Tabel SQL |
|----|----------------|-----|----------------------|-----------------------------------------|
| 1  | **Rofiq Muallif** | 242520020 | **Modul Anggota** | CRUD Data Anggota, Fitur Pencarian (*Search*), dan Implementasi *Inheritance* (`Anggota extends BaseEntity`). Mengelola tabel `anggota`. |
| 2  | **CHAERANI NUR AZIZAH** | 242520034 | **Modul Produk** | CRUD Barang Koperasi (Sembako/Kebutuhan Utama), Stok Opname, dan Fitur Pencarian Produk. Mengelola tabel `produk`. |
| 3  | **WAHID IBNU HASAN** | 242520037 | **Modul Simpan Pinjam** | Pengelolaan simpanan (Wajib, Sukarela) serta Pengajuan & Pelunasan Cicilan Pinjaman. Mengelola tabel `simpanan` dan `pinjaman`. |
| 4  | **AKIRA ARNOVYA AVANZA** | 242520021 | **Modul Transaksi** | Pencatatan Jurnal Transaksi Penjualan Produk Toko Koperasi kepada Anggota dan Integrasi Cetak Laporan PDF. Mengelola tabel `transaksi`. |

---

## 🔐 Kolaborasi Tim: Fitur Login & Registrasi (Poin d)

Sesuai dengan ketentuan tugas bahwa nilai Login & Registrasi adalah **Nilai Tim**, kami membagi pengerjaannya ke dalam komponen arsitektur kode berikut agar adil dan terstruktur:

* 👤 **Rofiq Muallif** $\rightarrow$ **Database & Query (Backend):** Membuat skema tabel `users` di MySQL dan menyusun fungsi query SQL di `UserDAO.java` (`registerUser` & `checkLogin`).
* 💰 **Hasan** $\rightarrow$ **Logika Kontrol & Validasi (Controller):** Menghubungkan antarmuka UI ke database, mengecek validasi kecocokan password, verifikasi hak akses (`role: admin/petugas`), serta mengatur perpindahan halaman (*switch scene*).
* 📦 **Caca** $\rightarrow$ **Desain UI Register (Frontend):** Membuat tampilan form pendaftaran akun baru menggunakan komponen JavaFX (`RegisterView.java`).
* 📑 **Akira** $\rightarrow$ **Desain UI Login (Frontend):** Membuat tampilan form masuk ke sistem menggunakan komponen JavaFX (`LoginView.java`).

---

## 📋 Checklist Pemenuhan Syarat Tugas

Berikut adalah poin-poin penilaian tugas yang telah berhasil diimplementasikan di dalam sistem:

- [x] **Aplikasi Berbasis GUI (Nilai Maksimal):** Menggunakan JavaFX (Bukan CLI).
- [x] **Proses Autentikasi (Poin d):** Fitur Login dan Registrasi User berhasil terintegrasi secara tim menggunakan tabel `users` (mendukung role `admin` dan `petugas`).
- [x] **Proses CRUD & Database (Poin a):** Data tersimpan secara relasional di MySQL dengan fitur pencarian data di setiap modul.
- [x] **Penerapan Konsep OOP & Struktur Data (Poin b):**
  - [x] *Inheritance* (Pewarisan) pada model data (Contoh: `Anggota extends BaseEntity`).
  - [x] Mengelola mutasi data internal dengan `ArrayList` / Array.
  - [x] Menggunakan Kondisi (`if-else`) untuk validasi data dan Perulangan (`while` / `for`) saat mengambil data dari `ResultSet` database.
- [x] **Library Khusus (Poin a):** Menggunakan `itextpdf` untuk meng-export nota transaksi toko dan mutasi kas menjadi file PDF resmi.

---

## 🛠️ Panduan Persiapan & Cara Menjalankan

Bagi Dosen atau Asisten Laboratorium yang ingin menguji aplikasi ini, mohon ikuti langkah-langkah berikut:

### 1. Konfigurasi Database (MySQL)
1. Aktifkan XAMPP (Pastikan service **Apache** & **MySQL** berjalan).
2. Buka phpMyAdmin (`http://localhost/phpmyadmin/`).
3. Buat database baru dengan nama exact: **`koperasi_merah_putih`**.
4. Masuk ke database tersebut, pilih menu **Import**, lalu arahkan ke file SQL yang berada di dalam folder proyek: `/src/database/koperasi_merah_putih.sql`.

### 2. Informasi Akun untuk Pengujian (Testing)
Tabel `users` telah dilengkapi dengan enkripsi teks biasa untuk kemudahan demo. Anda bisa masuk menggunakan salah satu akun bawaan berikut:
* **Level Admin:**
  * Username: `admin`
  * Password: `admin123`
* **Level Petugas:**
  * Username: `petugas`
  * Password: `petugas123`

### 3. Library yang Digunakan (Dependencies)
Pastikan library berikut sudah dimasukkan ke build path / dependencies proyek di IDE Anda:
* `mysql-connector-j-9.7.0.jar` (Driver Konektor Database)
* `itextpdf-5.5.13.3.jar` (Library Pembuat Dokumen PDF)
* JavaFX SDK (Sesuai versi environment runtime komputer Anda)

---

## 🗄️ Skema & Struktur Database

Aplikasi ini menggunakan total **6 tabel** yang saling berelasi:
1. **`users`**: Menyimpan data akun login sistem (Admin/Petugas).
2. **`anggota`**: Data profil lengkap anggota koperasi (Key utama identitas).
3. **`produk`**: Katalog persediaan barang kebutuhan pokok di koperasi.
4. **`simpanan`**: Log riwayat tabungan (Pokok, Wajib, Sukarela) yang terikat ke `id_anggota`.
5. **`pinjaman`**: Data pinjaman dana, cicilan per bulan, dan status kelunasan anggota.
6. **`transaksi`**: Transaksi kasir penjualan `produk` kepada `anggota` tertentu secara *real-time*.

---

## 📂 Struktur Direktori Proyek

```text
BIG PROJECT/
├── .vscode/             # Konfigurasi editor VS Code
├── lib/                 # Folder library eksternal (.jar)
├── src/                 # Source Code Utama
│   ├── app/             # Main Class & Launcher Aplikasi
│   ├── database/        # Dump File SQL (.sql) & Class DbConnection
│   ├── model/           # Kelas Model / Entitas Data (POJO & Inheritance)
│   ├── dao/             # Data Access Object (Query CRUD Insert, Select, Update, Delete)
│   ├── view/            # Tampilan Antarmuka GUI JavaFX (.fxml atau Java code)
│   └── test/            # File uji coba fungsionalitas kode
└── README.md            # Dokumentasi utama proyek
