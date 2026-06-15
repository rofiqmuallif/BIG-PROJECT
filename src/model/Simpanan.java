package model;

public class Simpanan {
    private int id;
    private int idAnggota;
    private String jenisSimpanan;
    private double jumlah;
    private String tanggal;
    private String keterangan; // Tambahan sesuai DB

    public Simpanan(int id, int idAnggota, String jenisSimpanan, double jumlah, String tanggal, String keterangan) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.jenisSimpanan = jenisSimpanan;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public int getId() {
        return id;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public String getJenisSimpanan() {
        return jenisSimpanan;
    }

    public double getJumlah() {
        return jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setJenisSimpanan(String jenisSimpanan) {
        this.jenisSimpanan = jenisSimpanan;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}