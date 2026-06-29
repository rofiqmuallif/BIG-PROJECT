package dao;

import database.DatabaseConnection;
import model.Produk;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {

    private Connection conn;

    public ProdukDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    public boolean tambahProduk(Produk produk) {
        String sql = "INSERT INTO produk (kode_produk, nama_produk, harga, stok, satuan) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produk.getKodeProduk());
            ps.setString(2, produk.getNamaProduk());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.setString(5, produk.getSatuan());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal tambah produk: " + e.getMessage());
            return false;
        }
    }

    public List<Produk> getAllProduk() {
        List<Produk> list = new ArrayList<>();
        String sql = "SELECT * FROM produk";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Produk p = new Produk(
                        rs.getInt("id"),
                        rs.getString("kode_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stok"),
                        rs.getString("satuan")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data produk: " + e.getMessage());
        }
        return list;
    }

    public Produk getProdukById(int id) {
        String sql = "SELECT * FROM produk WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produk(
                        rs.getInt("id"),
                        rs.getString("kode_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stok"),
                        rs.getString("satuan")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal cari produk by ID: " + e.getMessage());
        }
        return null;
    }

    public Produk getProdukByKode(String kode) {
        String sql = "SELECT * FROM produk WHERE kode_produk = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Produk(
                        rs.getInt("id"),
                        rs.getString("kode_produk"),
                        rs.getString("nama_produk"),
                        rs.getDouble("harga"),
                        rs.getInt("stok"),
                        rs.getString("satuan")
                );
            }
        } catch (SQLException e) {
            System.out.println("Gagal cari produk by kode: " + e.getMessage());
        }
        return null;
    }

    public boolean updateProduk(Produk produk) {
        String sql = "UPDATE produk SET kode_produk=?, nama_produk=?, harga=?, stok=?, satuan=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produk.getKodeProduk());
            ps.setString(2, produk.getNamaProduk());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.setString(5, produk.getSatuan());
            ps.setInt(6, produk.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update produk: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusProduk(int id) {
        String sql = "DELETE FROM produk WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal hapus produk: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStok(int id, int stokBaru) {
        String sql = "UPDATE produk SET stok = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update stok: " + e.getMessage());
            return false;
        }
    }
}