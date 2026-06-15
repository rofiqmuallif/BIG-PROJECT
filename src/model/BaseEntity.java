package model;

import java.time.LocalDateTime;

public abstract class BaseEntity {

    protected int id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor kosong
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor dengan id
    public BaseEntity(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Abstract method — wajib di-override tiap child class
    public abstract String getDisplayName();

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Override toString — juga memenuhi kriteria "mengelola data"
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", name=" + getDisplayName() + "]";
    }
}