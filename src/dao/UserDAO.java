package dao;

import database.DatabaseConnection; 
import model.User; 
import java.sql.*; 

public class UserDAO {
    private Connection conn = DatabaseConnection.getConnection();

    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
            );
        }
        return null; 
    }

    public boolean registrasi(String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, role);
        return ps.executeUpdate() > 0;
    }

    public boolean usernameAda(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        return ps.executeQuery().next();
    }
}