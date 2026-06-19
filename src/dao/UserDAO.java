package dao;

import database.DatabaseConnection; // Menghubungkan ke helper koneksi database.
import model.User; // Model user.
import java.sql.*; // Kelas JDBC: Connection, PreparedStatement, ResultSet, SQLException.

// DAO untuk tabel users: menangani login, registrasi, dan pemeriksaan username.
public class UserDAO {
    // Mengambil koneksi dari DatabaseConnection (dibuat sekali dan dipakai ulang).
    private Connection conn = DatabaseConnection.getConnection();

    // LOGIN: mencari baris users yang cocok username + password.
    // Jika ditemukan, kembalikan object User; jika tidak, kembalikan null.
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        // Set nilai parameter pada prepared statement untuk mencegah SQL injection.
        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            // Jika ada hasil, bangun objek User dari kolom hasil query.
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            );
        }
        return null; // login gagal
    }

    // REGISTRASI: menyimpan user baru.
    public boolean registrasi(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, role);
        // executeUpdate mengembalikan banyaknya baris yang terpengaruh (>0 berarti sukses).
        return ps.executeUpdate() > 0;
    }

    // CEK apakah username sudah ada (dipakai) — dipakai saat registrasi untuk validasi.
    public boolean usernameAda(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        // Jika ResultSet punya baris, berarti username ditemukan.
        return ps.executeQuery().next();
    }
}