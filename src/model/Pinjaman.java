package model;

public class Pinjaman {
    private int id;
    private String kodePinjaman; // Penyesuaian DB
    private int idAnggota;
    private double jumlahPinjam; // Penyesuaian DB
    private double jumlahBayar; // Penyesuaian DB
    private double cicilanPerBulan; // Penyesuaian DB
    private String status;
    private String tanggalPinjam; // Penyesuaian DB

    public Pinjaman(int id, String kodePinjaman, int idAnggota, double jumlahPinjam,
            double jumlahBayar, double cicilanPerBulan, String status, String tanggalPinjam) {
        this.id = id;
        this.kodePinjaman = kodePinjaman;
        this.idAnggota = idAnggota;
        this.jumlahPinjam = jumlahPinjam;
        this.jumlahBayar = jumlahBayar;
        this.cicilanPerBulan = cicilanPerBulan;
        this.status = status;
        this.tanggalPinjam = tanggalPinjam;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getKodePinjaman() {
        return kodePinjaman;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public double getJumlahPinjam() {
        return jumlahPinjam;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public double getCicilanPerBulan() {
        return cicilanPerBulan;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    // Setters
    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}