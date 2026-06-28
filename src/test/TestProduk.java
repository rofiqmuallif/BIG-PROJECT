package test;

import dao.ProdukDAO;
import model.Produk;

import java.util.List;

public class TestProduk {

    public static void main(String[] args) {
        System.out.println("==============================");
        System.out.println("   TEST MODUL PRODUK - Caca  ");
        System.out.println("==============================");

        ProdukDAO dao = new ProdukDAO();

        System.out.println("\n[1] Test Tambah Produk...");
        Produk produkBaru = new Produk("PRD001", "Beras Premium 5kg", 65000, 50, "Karung");
        boolean tambah = dao.tambahProduk(produkBaru);
        System.out.println(tambah ? "✓ Tambah berhasil!" : "✗ Tambah gagal.");

        Produk produkBaru2 = new Produk("PRD002", "Gula Pasir 1kg", 14000, 100, "Bungkus");
        boolean tambah2 = dao.tambahProduk(produkBaru2);
        System.out.println(tambah2 ? "✓ Tambah produk ke-2 berhasil!" : "✗ Tambah produk ke-2 gagal.");

        System.out.println("\n[2] Test Ambil Semua Produk...");
        List<Produk> semua = dao.getAllProduk();
        if (!semua.isEmpty()) {
            System.out.println("✓ Total produk: " + semua.size());
            for (Produk p : semua) {
                System.out.println("  → " + p);
            }
        } else {
            System.out.println("✗ Tidak ada data produk.");
        }

        System.out.println("\n[3] Test Cari Produk by Kode 'PRD001'...");
        Produk cari = dao.getProdukByKode("PRD001");
        if (cari != null) {
            System.out.println("✓ Ditemukan: " + cari);

            System.out.println("\n[4] Test Update Produk ID=" + cari.getId() + "...");
            cari.setHarga(67000);
            cari.setStok(45);
            boolean update = dao.updateProduk(cari);
            System.out.println(update ? "✓ Update berhasil!" : "✗ Update gagal.");

            System.out.println("\n[5] Test Update Stok...");
            boolean stokOk = dao.updateStok(cari.getId(), 40);
            System.out.println(stokOk ? "✓ Update stok berhasil!" : "✗ Update stok gagal.");

            System.out.println("\n[6] Test Hapus Produk ID=" + cari.getId() + "...");
            boolean hapus = dao.hapusProduk(cari.getId());
            System.out.println(hapus ? "✓ Hapus berhasil!" : "✗ Hapus gagal.");
        } else {
            System.out.println("✗ Produk PRD001 tidak ditemukan (mungkin test tambah gagal).");
        }

        System.out.println("\n==============================");
        System.out.println("   TEST SELESAI               ");
        System.out.println("==============================");
    }
}
