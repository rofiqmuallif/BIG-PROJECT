package model;

public class Anggota extends BaseEntity {
    private String nik;           
    private String nama;          
    private String alamat;        
    private String noHp;          
    private String status;        
    private String tanggalDaftar; 

    public Anggota(int id, String nik, String nama,
                   String alamat, String noHp, String status, String tanggalDaftar) {
        super(id); 
        this.nik = nik;
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
        this.status = status;
        this.tanggalDaftar = tanggalDaftar;
    }

    @Override
    public String getDisplayName() {
        return nama;
    }

    public int getId() { return id; }
    public String getNik() { return nik; }
    public String getNama() { return nama; }
    public String getAlamat() { return alamat; }
    public String getNoHp() { return noHp; }
    public String getStatus() { return status; }
    public String getTanggalDaftar() { return tanggalDaftar; }

    public void setNama(String nama) { this.nama = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    public void setStatus(String status) { this.status = status; }

    public String getTelepon() {
        return noHp;
    }
}