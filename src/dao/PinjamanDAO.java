package dao;

import database.DatabaseConnection;
import model.Pinjaman;
import java.sql.*;
import java.util.*;

public class PinjamanDAO {
    private Connection conn = DatabaseConnection.getConnection();

    public boolean tambah(Pinjaman p) throws SQLException {
        String sql = "INSERT INTO pinjaman (kode_pinjaman, id_anggota, jumlah_pinjam, jumlah_bayar, cicilan_per_bulan, status, tanggal_pinjam) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getKodePinjaman());
        ps.setInt(2, p.getIdAnggota());
        ps.setDouble(3, p.getJumlahPinjam());
        ps.setDouble(4, p.getJumlahBayar());
        ps.setDouble(5, p.getCicilanPerBulan());
        ps.setString(6, p.getStatus());
        ps.setString(7, p.getTanggalPinjam());
        return ps.executeUpdate() > 0;
    }

    public List<Pinjaman> getAll() throws SQLException {
        List<Pinjaman> list = new ArrayList<>();
        String sql = "SELECT * FROM pinjaman";
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
            list.add(new Pinjaman(
                    rs.getInt("id"), rs.getString("kode_pinjaman"),
                    rs.getInt("id_anggota"), rs.getDouble("jumlah_pinjam"),
                    rs.getDouble("jumlah_bayar"), rs.getDouble("cicilan_per_bulan"),
                    rs.getString("status"), rs.getString("tanggal_pinjam")));
        }
        return list;
    }

    public boolean bayarCicilan(int id, double bayarBaru) throws SQLException {
        String sql = "UPDATE pinjaman SET jumlah_bayar = jumlah_bayar + ? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDouble(1, bayarBaru);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }

    public boolean updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE pinjaman SET status=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    }

    public boolean hapus(int id) throws SQLException {
        String sql = "DELETE FROM pinjaman WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }
}