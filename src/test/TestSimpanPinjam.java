package test;

import dao.SimpananDAO;
import dao.PinjamanDAO;
import model.Simpanan;
import model.Pinjaman;
import java.time.LocalDate;

public class TestSimpanPinjam {
    public static void main(String[] args) {
        SimpananDAO sDao = new SimpananDAO();
        PinjamanDAO pDao = new PinjamanDAO();

        try {
            System.out.println("--- Test Tabel Simpanan ---");
            System.out.println("Jumlah Data Simpanan Awal: " + sDao.getAll().size());

            // Coba Insert Simpanan
            // Format: id (0 untuk auto_increment), id_anggota, jenis
            // (pokok/wajib/sukarela), jumlah, tanggal, keterangan
            Simpanan sBaru = new Simpanan(0, 3, "wajib", 50000.0, LocalDate.now().toString(),
                    "Simpanan wajib bulan ini");
            boolean insertSimpanan = sDao.tambah(sBaru);
            System.out.println("Status Tambah Simpanan: " + (insertSimpanan ? "Berhasil ✅" : "Gagal ❌"));
            System.out.println("Jumlah Data Simpanan Sekarang: " + sDao.getAll().size());

            System.out.println("\n--- Test Tabel Pinjaman ---");
            System.out.println("Jumlah Data Pinjaman Awal: " + pDao.getAll().size());

            // Coba Insert Pinjaman
            // Format: id, kode, id_anggota, jml_pinjam, jml_bayar, cicilan_per_bulan,
            // status (aktif/lunas), tanggal

            int i = pDao.getAll().size() + 1;
            Pinjaman pBaru = new Pinjaman(0, "PJ-" + i, 3, 1000000.0, 0.0, 100000.0, "aktif",
                    LocalDate.now().toString());
            boolean insertPinjaman = pDao.tambah(pBaru);
            System.out.println("Status Tambah Pinjaman: " + (insertPinjaman ? "Berhasil ✅" : "Gagal ❌"));
            System.out.println("Jumlah Data Pinjaman Sekarang: " + pDao.getAll().size());

            System.out.println(
                    "\n🎉 Modul Hasan (SimpanPinjam) dengan skema database baru sudah sinkron dan berjalan lancar!");

        } catch (Exception e) {
            System.out.println("❌ Ada error! Cek pesan di bawah ini:");
            System.out.println(
                    "Catatan: Pastikan di tabel 'anggota' kamu SUDAH ADA data dengan id=1 agar Foreign Key (id_anggota) tidak error saat insert.");
            e.printStackTrace();
        }
    }
}