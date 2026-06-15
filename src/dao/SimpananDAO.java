package dao;

import database.DatabaseConnection;
import model.Simpanan;
import java.sql.*;
import java.util.*;

public class SimpananDAO {
    private Connection conn = DatabaseConnection.getConnection();

    public boolean tambah(Simpanan s) throws SQLException {
        String sql = "INSERT INTO simpanan (id_anggota, jenis_simpanan, jumlah, tanggal, keterangan) VALUES (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, s.getIdAnggota());
        ps.setString(2, s.getJenisSimpanan());
        ps.setDouble(3, s.getJumlah());
        ps.setString(4, s.getTanggal());
        ps.setString(5, s.getKeterangan());
        return ps.executeUpdate() > 0;
    }

    public List<Simpanan> getAll() throws SQLException {
        List<Simpanan> list = new ArrayList<>();
        String sql = "SELECT * FROM simpanan";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            list.add(new Simpanan(
                    rs.getInt("id"), rs.getInt("id_anggota"),
                    rs.getString("jenis_simpanan"), rs.getDouble("jumlah"),
                    rs.getString("tanggal"), rs.getString("keterangan")));
        }
        return list;
    }

    public boolean update(Simpanan s) throws SQLException {
        String sql = "UPDATE simpanan SET jenis_simpanan=?, jumlah=?, keterangan=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, s.getJenisSimpanan());
        ps.setDouble(2, s.getJumlah());
        ps.setString(3, s.getKeterangan());
        ps.setInt(4, s.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean hapus(int id) throws SQLException {
        String sql = "DELETE FROM simpanan WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}