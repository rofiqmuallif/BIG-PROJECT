package database;

import java.sql.*;

// Kelas utilitas untuk mengelola koneksi ke database MySQL.
// Memakai pola singleton sederhana: koneksi dibuat sekali, lalu dipakai ulang.
public class DatabaseConnection {
    // URL koneksi ke database (pastikan database dan schema sudah dibuat).
    private static final String URL = "jdbc:mysql://localhost:3306/koperasi_merah_putih";
    // User dan password untuk koneksi (sesuaikan dengan konfigurasi MySQL Anda).
    private static final String USER = "root";
    private static final String PASSWORD = ""; // sesuaikan kalau ada password

    // Menyimpan instance Connection agar tidak membuka koneksi berulang.
    private static Connection connection = null;

    // Mendapatkan koneksi: jika belum ada atau tertutup, buka koneksi baru.
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Memuat driver JDBC MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Membuka koneksi ke database
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi berhasil!");
            }
        } catch (Exception e) {
            // Jangan lempar exception langsung di UI; cukup log/print agar tester tahu.
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return connection;
    }
}