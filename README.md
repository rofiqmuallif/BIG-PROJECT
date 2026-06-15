# 🏦 Sistem Informasi Koperasi Merah Putih (JavaFX)

Aplikasi Sistem Informasi Koperasi berbasis Desktop yang dibuat menggunakan **JavaFX**, menggunakan database **MySQL**, serta mengintegrasikan library **iTextPDF** untuk pencetakan laporan transaksi. Proyek ini dibuat untuk memenuhi tugas besar mata kuliah Pengembangan Sistem Informasi.

---

## 👥 Kelompok & Pembagian Tugas (Modul Utama)

Setiap anggota kelompok bertanggung jawab penuh atas modul utama masing-masing (termasuk file Model, DAO, View, dan Test di dalam foldernya):

| No | Nama Mahasiswa | NIM | Tanggung Jawab Modul | Detail Fitur Utama |
|----|----------------|-----|----------------------|--------------------|
| 1  | **Rofiq Muallif** | 242520020 | **Modul Anggota** | CRUD Data Anggota, Fitur Pencarian (*Search*), dan Implementasi *Inheritance* (`Anggota extends BaseEntity`). |
| 2  | **CHAERANI NUR AZIZAH** | 242520034 | **Modul Produk** | CRUD Paket Simpanan, Layanan, atau Barang Koperasi, serta Fitur Pencarian Produk. |
| 3  | **WAHID IBNU HASAN** | 242520037 | **Modul Simpan Pinjam** | Pengelolaan Data Setoran Simpanan, Pengajuan Pinjaman, Mutasi Array/List Saldo. |
| 4  | **AKIRA ARNOVYA AVANZA** | 242520021 | **Modul Transaksi** | Pencatatan Jurnal Transaksi Kasir, Filter Tanggal, dan Integrasi Cetak PDF. |

---

## 🔐 Kolaborasi Tim: Fitur Login & Registrasi (Poin d)

Sesuai dengan ketentuan tugas bahwa nilai Login & Registrasi adalah **Nilai Tim**, kami membagi pengerjaannya ke dalam komponen arsitektur kode berikut agar adil dan terstruktur:

* 👤 **Rofiq Muallif** $\rightarrow$ **Database & Query (Backend):** Membuat tabel `users` di MySQL dan menyusun fungsi query SQL di `UserDAO.java` (`registerUser` & `checkLogin`).
* 💰 **Hasan** $\rightarrow$ **Logika Kontrol & Validasi (Controller):** Menghubungkan antarmuka UI ke database, mengecek validasi kecocokan password, serta mengatur perpindahan halaman (*switch scene*).
* 📦 **Caca** $\rightarrow$ **Desain UI Register (Frontend):** Membuat tampilan form pendaftaran akun baru menggunakan komponen JavaFX (`RegisterView.java`).
* 📑 **Akira** $\rightarrow$ **Desain UI Login (Frontend):** Membuat tampilan form masuk ke sistem menggunakan komponen JavaFX (`LoginView.java`).

---

## 📋 Checklist Pemenuhan Syarat Tugas

Berikut adalah poin-poin penilaian tugas yang telah berhasil diimplementasikan di dalam sistem:

- [x] **Aplikasi Berbasis GUI (Nilai Maksimal):** Menggunakan JavaFX (Bukan CLI).
- [x] **Proses Autentikasi (Poin d):** Fitur Login dan Registrasi User berhasil terintegrasi secara tim.
- [x] **Proses CRUD & Database (Poin a):** Data wajib tersimpan di MySQL, lengkap dengan fitur *Search* (Pencarian).
- [x] **Penerapan Konsep OOP & Struktur Data (Poin b):**
  - [x] *Inheritance* (Pewarisan) pada model data (Contoh: `Anggota extends BaseEntity`).
  - [x] Mengelola data dengan `ArrayList` / Array.
  - [x] Menggunakan Kondisi (`if-else`) dan Perulangan (`while` / `for`) pada logika bisnis dan DAO.
- [x] **Library Khusus (Poin a):** Menggunakan `itextpdf` untuk meng-export riwayat transaksi menjadi file PDF resmi.

---

## 🛠️ Panduan Persiapan & Cara Menjalankan

Bagi Dosen atau Asisten Laboratorium yang ingin menguji aplikasi ini, mohon ikuti langkah-langkah berikut:

### 1. Konfigurasi Database (MySQL)
1. Aktifkan XAMPP (Apache & MySQL).
2. Buat database baru bernama: `koperasi_merah_putih`.
3. Import file database yang berada di dalam folder proyek: `/src/database/` (jalankan query DDL yang disediakan).

### 2. Informasi Akun untuk Pengujian (Testing)
Untuk mencoba fitur masuk ke sistem tanpa registrasi ulang, gunakan akun *default* berikut:
* **Username:** `admin`
* **Password:** `admin123`

### 3. Library yang Digunakan (Dependencies)
Pastikan library berikut sudah ter-load di build path IDE Anda:
* `mysql-connector-j-9.7.0.jar` (Driver Database MySQL)
* `itextpdf-5.5.13.3.jar` (Library Cetak PDF)
* JavaFX SDK 

---

## 📂 Struktur Direktori Proyek

```text
BIG PROJECT/
├── .vscode/             # Konfigurasi editor VS Code
├── lib/                 # Tempat Library eksternal (JAR)
├── src/                 # Source Code Utama
│   ├── app/             # Main Class & Launcher
│   ├── database/        # Pengaturan & File SQL Database
│   ├── model/           # Kelas Model / Entitas data
│   ├── dao/             # Query CRUD SQL ke Database
│   ├── view/            # Tampilan GUI JavaFX per Modul
│   └── test/            # File unit testing mandiri
└── README.md            # Dokumentasi proyek