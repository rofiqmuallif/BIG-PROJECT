// File ini menghubungkan aplikasi Java dengan tabel anggota di database.
// Class ini disebut DAO (Data Access Object), tugasnya mengurus operasi CRUD.

package dao;

import database.DatabaseConnection; // Import class koneksi database.
import model.Anggota; // Import model data anggota.
import java.sql.*; // Import semua class SQL yang dibutuhkan.
import java.util.*; // Import kelas List dan ArrayList.

public class AnggotaDAO {
    // Membuat koneksi ke database sekali dan digunakan berulang-ulang.
    private Connection conn = DatabaseConnection.getConnection();

    // Method tambah untuk menyimpan data anggota baru ke tabel anggota.
    public boolean tambah(Anggota a) throws SQLException {
        // SQL untuk memasukkan data ke tabel anggota.
        String sql = "INSERT INTO anggota (nik, nama, alamat, no_hp, status, tanggal_daftar) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        // Mengisi tanda tanya (?) sesuai urutan kolom.
        ps.setString(1, a.getNik());
        ps.setString(2, a.getNama());
        ps.setString(3, a.getAlamat());
        ps.setString(4, a.getNoHp());
        ps.setString(5, a.getStatus());
        ps.setString(6, a.getTanggalDaftar());

        // executeUpdate() mengembalikan jumlah baris yang terpengaruh.
        return ps.executeUpdate() > 0;
    }

    // Method untuk mengambil semua data anggota dari database.
    public List<Anggota> getAll() throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";

        // Menjalankan query dan menerima hasilnya dalam ResultSet.
        ResultSet rs = conn.createStatement().executeQuery(sql);

        // while (rs.next()) artinya lanjutkan selama masih ada data berikutnya.
        while (rs.next()) {
            // Membuat objek Anggota dari setiap baris hasil query.
            list.add(new Anggota(
                rs.getInt("id"), rs.getString("nik"),
                rs.getString("nama"), rs.getString("alamat"),
                rs.getString("no_hp"), rs.getString("status"),
                rs.getString("tanggal_daftar")
            ));
        }
        return list;
    }

    // Method untuk mencari anggota berdasarkan kata kunci.
    public List<Anggota> cari(String keyword) throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE nama LIKE ? OR nik LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        // Menambahkan % agar pencarian tidak case-sensitive dan bisa cocok sebagian.
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

    // Method untuk mengubah data anggota berdasarkan id.
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

    // Method untuk menghapus data anggota berdasarkan id.
    public boolean hapus(int id) throws SQLException {
        String sql = "DELETE FROM anggota WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }
}