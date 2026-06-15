package library.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.Anggota;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfExporter {

    public static void exportAnggota(List<Anggota> listAnggota, String filePath) throws Exception {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        Font fontJudul = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font fontSub = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
        Font fontIsi = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

        Paragraph judul = new Paragraph("KOPERASI MERAH PUTIH", fontJudul);
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        Paragraph subjudul = new Paragraph("Laporan Data Anggota", fontSub);
        subjudul.setAlignment(Element.ALIGN_CENTER);
        document.add(subjudul);

        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm"));
        Paragraph tanggal = new Paragraph("Dicetak: " + waktu, fontSub);
        tanggal.setAlignment(Element.ALIGN_CENTER);
        document.add(tanggal);

        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.5f, 2f, 2f, 2f, 1.5f});

        BaseColor merah = new BaseColor(185, 28, 28);
        String[] headers = {"No", "NIK", "Nama", "Alamat", "Telepon"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
            cell.setBackgroundColor(merah);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(6);
            table.addCell(cell);
        }

        int no = 1;
        for (Anggota a : listAnggota) {
            BaseColor bg = (no % 2 == 0) ? new BaseColor(254, 242, 242) : BaseColor.WHITE;

            addCell(table, String.valueOf(no++), fontIsi, bg, Element.ALIGN_CENTER);
            addCell(table, a.getNik(), fontIsi, bg, Element.ALIGN_LEFT);
            addCell(table, a.getNama(), fontIsi, bg, Element.ALIGN_LEFT);
            addCell(table, a.getAlamat(), fontIsi, bg, Element.ALIGN_LEFT);
            addCell(table, a.getNoHp(), fontIsi, bg, Element.ALIGN_LEFT);
        }

        document.add(table);

        document.add(Chunk.NEWLINE);
        Paragraph footer = new Paragraph("Total anggota: " + listAnggota.size(), fontSub);
        document.add(footer);

        document.close();
    }

    private static void addCell(PdfPTable table, String text, Font font,
                                BaseColor bg, int align) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "-", font));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(align);
        cell.setPadding(5);
        table.addCell(cell);
    }
}