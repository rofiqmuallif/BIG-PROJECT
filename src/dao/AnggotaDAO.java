package dao;

import database.DatabaseConnection;
import model.Anggota;
import java.sql.*;
import java.util.*;

public class AnggotaDAO {
    private Connection conn = DatabaseConnection.getConnection();

    public boolean tambah(Anggota a) throws SQLException {
        String sql = "INSERT INTO anggota (nik, nama, alamat, no_hp, status, tanggal_daftar) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNik());
        ps.setString(2, a.getNama());
        ps.setString(3, a.getAlamat());
        ps.setString(4, a.getNoHp());
        ps.setString(5, a.getStatus());
        ps.setString(6, a.getTanggalDaftar());
        return ps.executeUpdate() > 0;
    }

    public List<Anggota> getAll() throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            list.add(new Anggota(
                rs.getInt("id"), rs.getString("nik"),
                rs.getString("nama"), rs.getString("alamat"),
                rs.getString("no_hp"), rs.getString("status"),
                rs.getString("tanggal_daftar")
            ));
        }
        return list;
    }

    public List<Anggota> cari(String keyword) throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE nama LIKE ? OR nik LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Anggota(
                rs.getInt("id"), rs.getString("nik"),
                rs.getString("nama"), rs.getString("alamat"),
                rs.getString("no_hp"), rs.getString("status"),
                rs.getString("tanggal_daftar")
            ));
        }
        return list;
    }

    public boolean update(Anggota a) throws SQLException {
        String sql = "UPDATE anggota SET nama=?, alamat=?, no_hp=?, status=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNama());
        ps.setString(2, a.getAlamat());
        ps.setString(3, a.getNoHp());
        ps.setString(4, a.getStatus());
        ps.setInt(5, a.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean hapus(int id) throws SQLException {
        String sql = "DELETE FROM anggota WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}