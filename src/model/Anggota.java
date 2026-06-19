package model;

// Kelas Anggota merepresentasikan data seorang anggota koperasi.
// Kelas ini mewarisi dari BaseEntity, jadi memiliki sifat dasar seperti id.
public class Anggota extends BaseEntity {
    // Variabel-variabel untuk menyimpan data anggota.
    private String nik;           // Nomor Induk Kependudukan
    private String nama;          // Nama lengkap anggota
    private String alamat;        // Alamat tempat tinggal
    private String noHp;          // Nomor telepon
    private String status;        // Status anggota (aktif / nonaktif)
    private String tanggalDaftar; // Tanggal saat data ditambahkan

    // Constructor untuk membuat objek Anggota baru dari data yang diterima.
    public Anggota(int id, String nik, String nama,
                   String alamat, String noHp, String status, String tanggalDaftar) {
        super(id); // Memanggil constructor kelas induk BaseEntity.
        this.nik = nik;
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
        this.status = status;
        this.tanggalDaftar = tanggalDaftar;
    }

    // Override method abstrak dari BaseEntity.
    // Digunakan untuk mengambil nama yang akan ditampilkan di UI atau log.
    @Override
    public String getDisplayName() {
        return nama;
    }

    // Getter untuk mengambil nilai dari setiap field.
    public int getId() { return id; }
    public String getNik() { return nik; }
    public String getNama() { return nama; }
    public String getAlamat() { return alamat; }
    public String getNoHp() { return noHp; }
    public String getStatus() { return status; }
    public String getTanggalDaftar() { return tanggalDaftar; }

    // Setter untuk mengubah nilai field tertentu.
    public void setNama(String nama) { this.nama = nama; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    public void setStatus(String status) { this.status = status; }

    // Method tambahan yang mempermudah akses nomor telepon.
    public String getTelepon() {
        return noHp;
    }
}