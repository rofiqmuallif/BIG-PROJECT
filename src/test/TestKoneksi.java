package test;

import database.DatabaseConnection;
import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Database terhubung, siap ngoding!");
        } else {
            System.out.println("❌ Koneksi gagal, cek MySQL/XAMPP dulu.");
        }
    }
}