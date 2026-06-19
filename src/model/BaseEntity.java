package model;

import java.time.LocalDateTime;

// BaseEntity menyediakan properti dasar yang dimiliki semua entitas,
// seperti id, createdAt, updatedAt, dan method abstrak getDisplayName().
public abstract class BaseEntity {

    // id disimpan protected agar child class bisa mengakses langsung jika perlu.
    protected int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor kosong: otomatis mengisi timestamp.
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor dengan id: dipakai saat memuat data dari database.
    public BaseEntity(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Method abstrak: setiap entitas harus memutuskan apa yang
    // ditampilkan sebagai nama/representasi singkat (mis. nama anggota).
    public abstract String getDisplayName();

    // Getters & Setters untuk properti dasar.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // toString yang berguna saat debugging: menampilkan class dan display name.
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", name=" + getDisplayName() + "]";
    }
}