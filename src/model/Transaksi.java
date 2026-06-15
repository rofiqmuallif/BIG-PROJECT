package model;

import java.time.LocalDate;

public class Transaksi {
    private int id;
    private String kodeTransaksi;
    private int idAnggota;
    private int idProduk;
    private int jumlah;
    private double totalHarga;
    private LocalDate tanggal;

    // Constructor kosong
    public Transaksi() {}

    // Constructor lengkap (dengan id, untuk hasil SELECT)
    public Transaksi(int id, String kodeTransaksi, int idAnggota, int idProduk,
                     int jumlah, double totalHarga, LocalDate tanggal) {
        this.id = id;
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    // Constructor tanpa id (untuk INSERT baru)
    public Transaksi(String kodeTransaksi, int idAnggota, int idProduk,
                     int jumlah, double totalHarga, LocalDate tanggal) {
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    // Getters
    public int getId() { return id; }
    public String getKodeTransaksi() { return kodeTransaksi; }
    public int getIdAnggota() { return idAnggota; }
    public int getIdProduk() { return idProduk; }
    public int getJumlah() { return jumlah; }
    public double getTotalHarga() { return totalHarga; }
    public LocalDate getTanggal() { return tanggal; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setKodeTransaksi(String kodeTransaksi) { this.kodeTransaksi = kodeTransaksi; }
    public void setIdAnggota(int idAnggota) { this.idAnggota = idAnggota; }
    public void setIdProduk(int idProduk) { this.idProduk = idProduk; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setTotalHarga(double totalHarga) { this.totalHarga = totalHarga; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }

    @Override
    public String toString() {
        return "Transaksi{" +
                "id=" + id +
                ", kodeTransaksi='" + kodeTransaksi + '\'' +
                ", idAnggota=" + idAnggota +
                ", idProduk=" + idProduk +
                ", jumlah=" + jumlah +
                ", totalHarga=" + totalHarga +
                ", tanggal=" + tanggal +
                '}';
    }
}