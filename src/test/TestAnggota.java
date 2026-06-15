package test;

import dao.AnggotaDAO;
import model.Anggota;

public class TestAnggota {
    public static void main(String[] args) throws Exception {
        AnggotaDAO dao = new AnggotaDAO();

        System.out.println("--- Semua Anggota ---");
        for (Anggota anggota : dao.getAll()) {
            System.out.println(anggota.getId() + " | " + anggota.getNama() + " | " + anggota.getStatus());
        }

        Anggota update = new Anggota(1, "", "Budi Santoso Updated",
                "Jl. Sudirman No.99", "089999999999", "aktif", "");
        System.out.println("\nUpdate: " + dao.update(update));

        System.out.println("\n--- Setelah Update ---");
        for (Anggota anggota : dao.getAll()) {
            System.out.println(anggota.getId() + " | " + anggota.getNama() + " | " + anggota.getAlamat());
        }

        System.out.println("\nHapus id=1: " + dao.hapus(1));

        System.out.println("\n--- Setelah Delete ---");
        System.out.println("Jumlah data: " + dao.getAll().size());
    }
}