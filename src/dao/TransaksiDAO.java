package dao;

import database.DatabaseConnection;
import model.Transaksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    // ===================== CREATE =====================
    public boolean tambahTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi (kode_transaksi, id_anggota, id_produk, jumlah, total_harga, tanggal) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getKodeTransaksi());
            ps.setInt(2, t.getIdAnggota());
            ps.setInt(3, t.getIdProduk());
            ps.setInt(4, t.getJumlah());
            ps.setDouble(5, t.getTotalHarga());
            ps.setDate(6, Date.valueOf(t.getTanggal()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal tambah: " + e.getMessage());
            return false;
        }
    }

    // ===================== READ ALL =====================
    public List<Transaksi> getAllTransaksi() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal getAll: " + e.getMessage());
        }
        return list;
    }

    // ===================== READ BY ID =====================
    public Transaksi getTransaksiById(int id) {
        String sql = "SELECT * FROM transaksi WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal getById: " + e.getMessage());
        }
        return null;
    }

    // ===================== READ BY KODE =====================
    public Transaksi getTransaksiByKode(String kode) {
        String sql = "SELECT * FROM transaksi WHERE kode_transaksi = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal getByKode: " + e.getMessage());
        }
        return null;
    }

    // ===================== READ BY ANGGOTA =====================
    public List<Transaksi> getTransaksiByAnggota(int idAnggota) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE id_anggota = ? ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAnggota);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal getByAnggota: " + e.getMessage());
        }
        return list;
    }

    // ===================== READ BY PRODUK =====================
    public List<Transaksi> getTransaksiByProduk(int idProduk) {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi WHERE id_produk = ? ORDER BY tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduk);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal getByProduk: " + e.getMessage());
        }
        return list;
    }

    // ===================== UPDATE =====================
    public boolean updateTransaksi(Transaksi t) {
        String sql = "UPDATE transaksi SET kode_transaksi=?, id_anggota=?, id_produk=?, " +
                     "jumlah=?, total_harga=?, tanggal=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getKodeTransaksi());
            ps.setInt(2, t.getIdAnggota());
            ps.setInt(3, t.getIdProduk());
            ps.setInt(4, t.getJumlah());
            ps.setDouble(5, t.getTotalHarga());
            ps.setDate(6, Date.valueOf(t.getTanggal()));
            ps.setInt(7, t.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal update: " + e.getMessage());
            return false;
        }
    }

    // ===================== DELETE =====================
    public boolean hapusTransaksi(int id) {
        String sql = "DELETE FROM transaksi WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[TransaksiDAO] Gagal hapus: " + e.getMessage());
            return false;
        }
    }

    // ===================== HELPER =====================
    private Transaksi mapRow(ResultSet rs) throws SQLException {
        return new Transaksi(
            rs.getInt("id"),
            rs.getString("kode_transaksi"),
            rs.getInt("id_anggota"),
            rs.getInt("id_produk"),
            rs.getInt("jumlah"),
            rs.getDouble("total_harga"),
            rs.getDate("tanggal").toLocalDate()
        );
    }
}