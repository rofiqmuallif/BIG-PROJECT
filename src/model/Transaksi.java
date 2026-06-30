package model;

import java.time.LocalDate;

public class Transaksi extends BaseEntity {
    
   
    private String kodeTransaksi;
    private int idAnggota;
    private int idProduk;
    private int jumlah;
    private double totalHarga;
    private LocalDate tanggal;

    public Transaksi(String kodeTransaksi, int idAnggota, int idProduk, int jumlah, double totalHarga, LocalDate tanggal) {
        super();
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    public Transaksi(int id, String kodeTransaksi, int idAnggota, int idProduk, int jumlah, double totalHarga, LocalDate tanggal) {
        super(id);
        this.kodeTransaksi = kodeTransaksi;
        this.idAnggota = idAnggota;
        this.idProduk = idProduk;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
    }

    @Override
    public String getDisplayName() {
        return kodeTransaksi;
    }

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
