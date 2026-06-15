package test;

import dao.TransaksiDAO;
import model.Transaksi;

import java.time.LocalDate;
import java.util.List;

public class TestTransaksi {

    static TransaksiDAO dao = new TransaksiDAO();
    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TEST MODUL TRANSAKSI - by Akira");
        System.out.println("========================================\n");

        testTambah();
        testGetAll();
        testGetById();
        testGetByKode();
        testGetByAnggota();
        testGetByProduk();
        testUpdate();
        testHapus();

        System.out.println("\n========================================");
        System.out.printf("HASIL: %d passed, %d failed.%n", passed, failed);
        System.out.println("========================================");
    }

    static void testTambah() {
        log("tambahTransaksi()");
        Transaksi t = new Transaksi("TRX-TEST-001", 1, 1, 3, 150000.0, LocalDate.now());
        boolean ok = dao.tambahTransaksi(t);
        check(ok, "Transaksi berhasil ditambahkan", "Gagal tambah transaksi");
    }

    static void testGetAll() {
        log("getAllTransaksi()");
        List<Transaksi> list = dao.getAllTransaksi();
        boolean ok = list != null && !list.isEmpty();
        check(ok, "Ditemukan " + (list != null ? list.size() : 0) + " transaksi", "List kosong atau null");
        if (ok) list.forEach(t -> System.out.println("       " + t));
    }

    static void testGetById() {
        log("getTransaksiById()");
        List<Transaksi> list = dao.getAllTransaksi();
        if (list == null || list.isEmpty()) { skip("Tidak ada data"); return; }
        int id = list.get(0).getId();
        Transaksi t = dao.getTransaksiById(id);
        check(t != null, "Transaksi ID " + id + " ditemukan", "Tidak ditemukan");
    }

    static void testGetByKode() {
        log("getTransaksiByKode(\"TRX-TEST-001\")");
        Transaksi t = dao.getTransaksiByKode("TRX-TEST-001");
        check(t != null, "Transaksi ditemukan: " + (t != null ? t.getKodeTransaksi() : "-"), "Kode tidak ditemukan");
    }

    static void testGetByAnggota() {
        log("getTransaksiByAnggota(id=1)");
        List<Transaksi> list = dao.getTransaksiByAnggota(1);
        check(list != null, "Ditemukan " + (list != null ? list.size() : 0) + " transaksi anggota 1", "Null");
    }

    static void testGetByProduk() {
        log("getTransaksiByProduk(id=1)");
        List<Transaksi> list = dao.getTransaksiByProduk(1);
        check(list != null, "Ditemukan " + (list != null ? list.size() : 0) + " transaksi produk 1", "Null");
    }

    static void testUpdate() {
        log("updateTransaksi()");
        Transaksi t = dao.getTransaksiByKode("TRX-TEST-001");
        if (t == null) { skip("Data TRX-TEST-001 tidak ada"); return; }
        t.setJumlah(5);
        t.setTotalHarga(250000.0);
        check(dao.updateTransaksi(t), "Update berhasil (jumlah=5, total=250000)", "Gagal update");
    }

    static void testHapus() {
        log("hapusTransaksi()");
        Transaksi t = dao.getTransaksiByKode("TRX-TEST-001");
        if (t == null) { skip("Data TRX-TEST-001 tidak ada"); return; }
        check(dao.hapusTransaksi(t.getId()), "Hapus ID " + t.getId() + " berhasil", "Gagal hapus");
    }

    // ===== Helpers =====
    static void log(String nama)  { System.out.println("[TEST] " + nama); }
    static void skip(String msg)  { System.out.println("  ⚠️  SKIP - " + msg); }
    static void check(boolean ok, String msgOk, String msgFail) {
        if (ok) { System.out.println("  ✅ PASS - " + msgOk); passed++; }
        else    { System.out.println("  ❌ FAIL - " + msgFail); failed++; }
    }
}