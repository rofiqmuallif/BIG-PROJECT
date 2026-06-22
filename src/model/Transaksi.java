package model;

import java.time.LocalDate;

// Menggabungkan dengan BaseEntity menggunakan kata kunci extends
public class Transaksi extends BaseEntity {
    
    // Properti id dihapus dari sini karena sudah ada di BaseEntity
    private String kodeTransaksi;
    private int idAnggota;
    private int idProduk;
    private int jumlah;
    private double totalHarga;
    private LocalDate tanggal;

    // Constructor 1: Tanpa ID (Digunakan saat membuat transaksi baru / INSERT ke database)
    public Transaksi(String kodeTransaksi, int idAnggota, int idProduk, int jumlah, double totalHarga, LocalDate tanggal) {
        super(); // Memanggil constructor kosong dari BaseEntity
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    // Constructor 2: Dengan ID (Digunakan saat mengambil data dari database / SELECT)
    public Transaksi(int id, String kodeTransaksi, int idAnggota, int idProduk, int jumlah, double totalHarga, LocalDate tanggal) {
        super(id); // Mengirimkan parameter id ke constructor BaseEntity
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    // Override method abstrak dari BaseEntity.
    // Gunakan kode transaksi sebagai nama tampilan singkat.
    @Override
    public String getDisplayName() {
        return kodeTransaksi;
    }

    // Getter dan Setter khusus untuk properti milik Transaksi saja
    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }
}
