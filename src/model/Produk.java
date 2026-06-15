package model;

public class Produk {
    private int id;
    private String kodeProduk;
    private String namaProduk;
    private double harga;
    private int stok;
    private String satuan;

    // Constructor kosong
    public Produk() {}

    // Constructor lengkap (untuk INSERT, tanpa id)
    public Produk(String kodeProduk, String namaProduk, double harga, int stok, String satuan) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
    }

    // Constructor lengkap (untuk SELECT, dengan id)
    public Produk(int id, String kodeProduk, String namaProduk, double harga, int stok, String satuan) {
        this.id = id;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.satuan = satuan;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKodeProduk() { return kodeProduk; }
    public void setKodeProduk(String kodeProduk) { this.kodeProduk = kodeProduk; }

    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public String getSatuan() { return satuan; }
    public void setSatuan(String satuan) { this.satuan = satuan; }

    @Override
    public String toString() {
        return String.format("[%s] %s - Rp%.0f | Stok: %d %s",
                kodeProduk, namaProduk, harga, stok, satuan);
    }
}