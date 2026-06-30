package view;

import dao.TransaksiDAO;
import model.Transaksi;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class TransaksiView extends Application {

    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private TableView<Transaksi> table = new TableView<>();
    private ObservableList<Transaksi> dataList = FXCollections.observableArrayList();

    private TextField txtCari = new TextField();
    private Button btnKembali = new Button("⬅ Kembali");

    private TextField txtKode       = new TextField();
    private TextField txtIdAnggota  = new TextField();
    private TextField txtIdProduk   = new TextField();
    private TextField txtJumlah     = new TextField();
    private TextField txtTotalHarga = new TextField();
    private DatePicker dpTanggal    = new DatePicker();
    private Label lblStatus         = new Label();

    private Transaksi selectedTransaksi = null;

    private String loggedInUser = "Admin"; 
    private String loggedInRole = "Admin"; 

    public TransaksiView() {}

    public TransaksiView(String username, String role) {
        this.loggedInUser = username;
        this.loggedInRole = role;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Manajemen Transaksi - Koperasi Merah Putih");

        btnKembali.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-min-width: 90px;");
        
        HBox topBarBox = new HBox(10, new SpacerRegion(), btnKembali);
        topBarBox.setPadding(new Insets(10, 15, 0, 15));
        topBarBox.setStyle("-fx-alignment: center-right;");

        Label lblTitle = new Label("Form Transaksi");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        dpTanggal.setValue(LocalDate.now());
        txtKode.setPromptText("Contoh: TRX-001");
        txtIdAnggota.setPromptText("ID Anggota");
        txtIdProduk.setPromptText("ID Produk");
        txtJumlah.setPromptText("Jumlah unit");
        txtTotalHarga.setPromptText("Total harga");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        form.add(new Label("Kode Transaksi:"), 0, 0); form.add(txtKode, 1, 0);
        form.add(new Label("ID Anggota:"),     0, 1); form.add(txtIdAnggota, 1, 1);
        form.add(new Label("ID Produk:"),      0, 2); form.add(txtIdProduk, 1, 2);
        form.add(new Label("Jumlah:"),         0, 3); form.add(txtJumlah, 1, 3);
        form.add(new Label("Total Harga (Rp):"),0, 4); form.add(txtTotalHarga, 1, 4);
        form.add(new Label("Tanggal:"),        0, 5); form.add(dpTanggal, 1, 5);

        Button btnTambah = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnHapus  = new Button("Hapus");
        Button btnClear  = new Button("Clear");
        Button btnExportPdf = new Button("📄 Export PDF"); 

        btnTambah.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-min-width: 80px;");
        btnUpdate.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-min-width: 80px;");
        btnHapus.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-min-width: 80px;");
        btnExportPdf.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-min-width: 100px; -fx-font-weight: bold;");

        HBox btnBox = new HBox(10, btnTambah, btnUpdate, btnHapus, btnClear, btnExportPdf);
        btnBox.setPadding(new Insets(5, 10, 5, 10));

        txtCari.setPromptText("Cari berdasarkan Kode / ID Anggota...");
        txtCari.setPrefWidth(250);

        HBox searchBarBox = new HBox(10, new Label("Cari Transaksi:"), txtCari);
        searchBarBox.setPadding(new Insets(5, 10, 5, 10));
        searchBarBox.setStyle("-fx-alignment: center-left;");

        TableColumn<Transaksi, Integer> colId    = col("ID", "id", 50);
        TableColumn<Transaksi, String>  colKode  = col("Kode", "kodeTransaksi", 100);
        TableColumn<Transaksi, Integer> colAngg  = col("ID Anggota", "idAnggota", 90);
        TableColumn<Transaksi, Integer> colProd  = col("ID Produk", "idProduk", 90);
        TableColumn<Transaksi, Integer> colJml   = col("Jumlah", "jumlah", 70);
        TableColumn<Transaksi, Double>  colTotal = col("Total Harga", "totalHarga", 120);
        TableColumn<Transaksi, LocalDate> colTgl = col("Tanggal", "tanggal", 100);

        table.getColumns().add(colId);
        table.getColumns().add(colKode);
        table.getColumns().add(colAngg);
        table.getColumns().add(colProd);
        table.getColumns().add(colJml);
        table.getColumns().add(colTotal);
        table.getColumns().add(colTgl);
        
        initSearchFilter();
        table.setPrefHeight(300);

        btnTambah.setOnAction(e -> handleTambah());
        btnUpdate.setOnAction(e -> handleUpdate());
        btnHapus.setOnAction(e -> handleHapus());
        btnClear.setOnAction(e -> clearForm());
        btnKembali.setOnAction(e -> handleKembali(stage));
        btnExportPdf.setOnAction(e -> handleExportPdf(stage)); 

        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) { selectedTransaksi = sel; populateForm(sel); }
        });

        VBox formBox = new VBox(10, lblTitle, form, btnBox, lblStatus);
        formBox.setPadding(new Insets(10, 15, 15, 15));

        VBox root = new VBox(10, topBarBox, formBox, new Separator(), searchBarBox, table);
        root.setPadding(new Insets(10));

        loadData();

        Scene scene = new Scene(root, 750, 720);
        stage.setScene(scene);
        stage.show();
    }

    private void initSearchFilter() {
        FilteredList<Transaksi> filteredData = new FilteredList<>(dataList, p -> true);

        txtCari.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transaksi -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase().trim();

                if (transaksi.getKodeTransaksi() != null && 
                    transaksi.getKodeTransaksi().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                if (String.valueOf(transaksi.getIdAnggota()).contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<Transaksi> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    private void handleKembali(Stage currentStage) {
        currentStage.close();
        try {
            Stage newStage = new Stage();
            new DashboardView(loggedInUser, loggedInRole).start(newStage);
        } catch (Exception ex) {
            System.out.println("Gagal membuka halaman dashboard: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleExportPdf(Stage stage) {
        if (dataList.isEmpty()) {
            setStatus("⚠️ Tidak ada data untuk diexport.", "orange");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Simpan Laporan Transaksi PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName("Laporan_Transaksi_" + LocalDate.now() + ".pdf");
        
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();
                
                Paragraph title = new Paragraph("LAPORAN DATA TRANSAKSI\nKOPERASI MERAH PUTIH\n\n");
                title.setAlignment(Element.ALIGN_CENTER); 
                document.add(title);
                
                PdfPTable pdfTable = new PdfPTable(7);
                pdfTable.setWidthPercentage(100);
                
                String[] headers = {"ID", "Kode", "ID Anggota", "ID Produk", "Jumlah", "Total Harga", "Tanggal"};
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(header));
                    pdfTable.addCell(cell);
                }
                
                for (Transaksi t : table.getItems()) {
                    pdfTable.addCell(String.valueOf(t.getId()));
                    pdfTable.addCell(t.getKodeTransaksi());
                    pdfTable.addCell(String.valueOf(t.getIdAnggota()));
                    pdfTable.addCell(String.valueOf(t.getIdProduk()));
                    pdfTable.addCell(String.valueOf(t.getJumlah()));
                    pdfTable.addCell(String.format("Rp %,.2f", t.getTotalHarga()));
                    pdfTable.addCell(t.getTanggal().toString());
                }
                
                document.add(pdfTable);
                document.close();
                
                setStatus("✅ Laporan Berhasil diexport ke PDF!", "green");
                
            } catch (Exception ex) {
                setStatus("❌ Gagal export PDF: " + ex.getMessage(), "red");
                ex.printStackTrace();
            }
        }
    }

    private void handleTambah() {
        try {
            Transaksi t = getFormData();
            if (transaksiDAO.tambahTransaksi(t)) {
                setStatus("✅ Transaksi berhasil ditambahkan.", "green");
                loadData(); clearForm();
            } else {
                setStatus("❌ Gagal menambahkan transaksi.", "red");
            }
        } catch (Exception ex) {
            setStatus("❌ Input tidak valid: " + ex.getMessage(), "red");
        }
    }

    private void handleUpdate() {
        if (selectedTransaksi == null) { setStatus("⚠️ Pilih transaksi dulu.", "orange"); return; }
        try {
            Transaksi t = getFormData();
            t.setId(selectedTransaksi.getId());
            if (transaksiDAO.updateTransaksi(t)) {
                setStatus("✅ Transaksi berhasil diupdate.", "green");
                loadData(); clearForm();
            } else {
                setStatus("❌ Gagal update transaksi.", "red");
            }
        } catch (Exception ex) {
            setStatus("❌ Input tidak valid: " + ex.getMessage(), "red");
        }
    }

    private void handleHapus() {
        if (selectedTransaksi == null) { setStatus("⚠️ Pilih transaksi dulu.", "orange"); return; }
        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION,
                "Hapus transaksi " + selectedTransaksi.getKodeTransaksi() + "?",
                ButtonType.YES, ButtonType.NO);
        konfirmasi.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.YES) {
                if (transaksiDAO.hapusTransaksi(selectedTransaksi.getId())) {
                    setStatus("✅ Transaksi berhasil dihapus.", "green");
                    loadData(); clearForm();
                } else {
                    setStatus("❌ Gagal hapus transaksi.", "red");
                }
            }
        });
    }

    private Transaksi getFormData() {
        String kode   = txtKode.getText().trim();
        int idAnggota = Integer.parseInt(txtIdAnggota.getText().trim());
        int idProduk  = Integer.parseInt(txtIdProduk.getText().trim());
        int jumlah    = Integer.parseInt(txtJumlah.getText().trim());
        double total  = Double.parseDouble(txtTotalHarga.getText().trim().replace(",", ""));
        LocalDate tgl = dpTanggal.getValue();

        if (kode.isEmpty()) throw new IllegalArgumentException("Kode transaksi wajib diisi.");
        if (tgl == null)    throw new IllegalArgumentException("Tanggal wajib diisi.");

        return new Transaksi(kode, idAnggota, idProduk, jumlah, total, tgl);
    }

    private void populateForm(Transaksi t) {
        txtKode.setText(t.getKodeTransaksi());
        txtIdAnggota.setText(String.valueOf(t.getIdAnggota()));
        txtIdProduk.setText(String.valueOf(t.getIdProduk()));
        txtJumlah.setText(String.valueOf(t.getJumlah()));
        txtTotalHarga.setText(String.valueOf(t.getTotalHarga()));
        dpTanggal.setValue(t.getTanggal());
    }

    private void clearForm() {
        txtKode.clear(); txtIdAnggota.clear(); txtIdProduk.clear();
        txtJumlah.clear(); txtTotalHarga.clear();
        dpTanggal.setValue(LocalDate.now());
        txtCari.clear();
        selectedTransaksi = null;
        table.getSelectionModel().clearSelection();
        lblStatus.setText("");
    }

    private void loadData() { dataList.setAll(transaksiDAO.getAllTransaksi()); }

    private void setStatus(String msg, String color) {
        lblStatus.setText(msg);
        lblStatus.setStyle("-fx-text-fill: " + color + ";");
    }

    private <S, T> TableColumn<S, T> col(String title, String prop, double width) {
        TableColumn<S, T> c = new TableColumn<>(title);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        c.setPrefWidth(width);
        return c;
    }

    public static void main(String[] args) { launch(args); }

    private static class SpacerRegion extends Region {
        public SpacerRegion() {
            HBox.setHgrow(this, Priority.ALWAYS);
        }
    }
}
