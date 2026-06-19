package test;

import dao.AnggotaDAO; // Import DAO untuk mengakses data anggota dari database.
import model.Anggota;  // Import model objek Anggota.

public class TestAnggota {
    // Method utama yang dijalankan saat program test dipanggil.
    public static void main(String[] args) throws Exception {
        // Membuat objek DAO untuk menjalankan operasi database.
        AnggotaDAO dao = new AnggotaDAO();

        // Menampilkan semua data anggota yang ada di database.
        System.out.println("--- Semua Anggota ---");

        // for-each digunakan untuk membaca semua objek Anggota dari hasil dao.getAll().
        for (Anggota anggota : dao.getAll()) {
            System.out.println(anggota.getId() + " | " + anggota.getNama() + " | " + anggota.getStatus());
        }

        // Membuat objek Anggota baru untuk proses update.
        // id=1 dipakai sebagai contoh, lalu data lain diupdate.
        Anggota update = new Anggota(
                1,
                "",
                "Budi Santoso Updated",
                "Jl. Sudirman No.99",
                "089999999999",
                "aktif",
                ""
        );

        // Menjalankan update dan menampilkan hasil true/false.
        System.out.println("\nUpdate: " + dao.update(update));

        // Menampilkan data setelah proses update.
        System.out.println("\n--- Setelah Update ---");
        for (Anggota anggota : dao.getAll()) {
            System.out.println(anggota.getId() + " | " + anggota.getNama() + " | " + anggota.getAlamat());
        }

        // Menjalankan proses hapus data dengan id 1.
        System.out.println("\nHapus id=1: " + dao.hapus(1));

        // Menampilkan jumlah data setelah delete.
        System.out.println("\n--- Setelah Delete ---");
        System.out.println("Jumlah data: " + dao.getAll().size());
    }
}