package model;

public class Pinjaman extends BaseEntity {

    private String kodePinjaman; 
    private int idAnggota;
    private double jumlahPinjam; 
    private double jumlahBayar; 
    private double cicilanPerBulan; 
    private String status;
    private String tanggalPinjam; 

    public Pinjaman(int id, String kodePinjaman, int idAnggota, double jumlahPinjam,
            double jumlahBayar, double cicilanPerBulan, String status, String tanggalPinjam) {
        super(id);
        this.kodePinjaman = kodePinjaman;
        this.idAnggota = idAnggota;
        this.jumlahPinjam = jumlahPinjam;
        this.jumlahBayar = jumlahBayar;
        this.cicilanPerBulan = cicilanPerBulan;
        this.status = status;
        this.tanggalPinjam = tanggalPinjam;
    }

    @Override
    public String getDisplayName() {
        return kodePinjaman;
    }

    
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

    
    public void setJumlahBayar(double jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
