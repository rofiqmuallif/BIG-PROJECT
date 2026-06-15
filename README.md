# 🏦 Sistem Informasi Koperasi Simpan Pinjam (JavaFX)

Aplikasi Sistem Informasi Koperasi berbasis Desktop yang dibuat menggunakan **JavaFX**, menggunakan database **MySQL**, serta mengintegrasikan library **iTextPDF** untuk pencetakan laporan transaksi. Proyek ini dibuat untuk memenuhi tugas besar mata kuliah Pengembangan Sistem Informasi.

---

## 👥 Kelompok & Pembagian Tugas (Modul)

Proses **Login & Registrasi** dikerjakan bersama oleh tim dengan pembagian arsitektur kode yang adil. Untuk modul utama, berikut adalah pembagian penanggung jawabnya:

| No | Nama Mahasiswa | NIM | Tanggung Jawab Modul | Detail Fitur |
|----|----------------|-----|----------------------|--------------|
| 1  | **Rofiq** (Kamu) | [Isi NIM] | **Modul Anggota** | CRUD Data Anggota, Pencarian Anggota, Implementasi OOP *Inheritance* |
| 2  | **Caca** | [Isi NIM] | **Modul Produk** | CRUD Paket Simpanan & Layanan Koperasi, Desain UI Login |
| 3  | **Hasan** | [Isi NIM] | **Modul Simpan Pinjam** | Pengajuan Pinjaman, Histori Simpanan, Setup Database & UserDAO |
| 4  | **Akira** | [Isi NIM] | **Modul Transaksi & Laporan** | Pencatatan Mutasi Kas, Desain UI Register, Integrasi Library iTextPDF |

---

## 📋 Checklist Pemenuhan Syarat Tugas

Berikut adalah poin-poin penilaian tugas yang telah berhasil diimplementasikan di dalam sistem:

- [x] **Aplikasi Berbasis GUI (Nilai Maksimal):** Menggunakan JavaFX (Bukan CLI).
- [x] **Proses Autentikasi (Poin d):** Fitur Login dan Registrasi User berhasil terintegrasi.
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
2. Buat database baru bernama: `db_koperasi`.
3. Import file database yang berada di dalam folder proyek: `/database/db_koperasi.sql` (atau jalankan query DDL yang disediakan).

### 2. Informasi Akun untuk Pengujian (Testing)
Untuk mencoba fitur masuk ke sistem tanpa registrasi ulang, gunakan akun *default* berikut:
* **Username:** `admin`
* **Password:** `admin123`

### 3. Library yang Digunakan (Dependencies)
Pastikan library berikut sudah ter-load di build path IDE Anda (NetBeans/IntelliJ/Eclipse):
* `mysql-connector-j` (Driver Database MySQL)
* `itextpdf-x.x.x.jar` (Library Cetak PDF)
* JavaFX SDK (Versi 11 atau yang terbaru)

---

## 📂 Struktur Direktori Proyek

```text
src/
├── database/            # File SQL Database Koperasi
├── library/             # Library Eksternal (iTextPDF)
├── model/               # Kelas Model/Entitas (Termasuk Inheritance)
├── dao/                 # Query SQL (Insert, Select, Update, Delete)
├── view/                # Tampilan Antarmuka GUI JavaFX
└── test/                # File Uji Coba Fungsi Mandiri