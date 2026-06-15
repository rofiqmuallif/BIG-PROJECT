package view;

import java.util.List;

import dao.AnggotaDAO;
import model.Anggota;
import library.pdf.PdfExporter;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AnggotaView extends Application {

    private AnggotaDAO dao = new AnggotaDAO();
    private TableView<Anggota> table = new TableView<>();
    private ObservableList<Anggota> dataList = FXCollections.observableArrayList();
    private String username;
    private String role;

    private TextField tfNik = new TextField();
    private TextField tfNama = new TextField();
    private TextField tfAlamat = new TextField();
    private TextField tfNoHp = new TextField();
    private ComboBox<String> cbStatus = new ComboBox<>();
    private TextField tfCari = new TextField();

    private int selectedId = -1;

    public AnggotaView() {
        this("", "");
    }

    public AnggotaView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {

        // ── TABEL ──
        TableColumn<Anggota, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(40);

        TableColumn<Anggota, String> colNik = new TableColumn<>("NIK");
        colNik.setCellValueFactory(new PropertyValueFactory<>("nik"));
        colNik.setPrefWidth(150);

        TableColumn<Anggota, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(150);

        TableColumn<Anggota, String> colAlamat = new TableColumn<>("Alamat");
        colAlamat.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        colAlamat.setPrefWidth(180);

        TableColumn<Anggota, String> colHp = new TableColumn<>("No HP");
        colHp.setCellValueFactory(new PropertyValueFactory<>("noHp"));
        colHp.setPrefWidth(120);

        TableColumn<Anggota, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(80);

        table.getColumns().addAll(colId, colNik, colNama, colAlamat, colHp, colStatus);
        table.setItems(dataList);
        table.setPrefHeight(250);

        // Klik baris tabel → isi form
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedId = newVal.getId();
                tfNik.setText(newVal.getNik());
                tfNama.setText(newVal.getNama());
                tfAlamat.setText(newVal.getAlamat());
                tfNoHp.setText(newVal.getNoHp());
                cbStatus.setValue(newVal.getStatus());
            }
        });

        // ── FORM INPUT ──
        tfNik.setPromptText("Masukkan NIK (16 digit)");
        tfNama.setPromptText("Masukkan Nama Lengkap");
        tfAlamat.setPromptText("Masukkan Alamat");
        tfNoHp.setPromptText("Masukkan No HP");
        cbStatus.getItems().addAll("aktif", "nonaktif");
        cbStatus.setValue("aktif");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        form.add(new Label("NIK"), 0, 0);
        form.add(tfNik, 1, 0);
        form.add(new Label("Nama"), 0, 1);
        form.add(tfNama, 1, 1);
        form.add(new Label("Alamat"), 0, 2);
        form.add(tfAlamat, 1, 2);
        form.add(new Label("No HP"), 0, 3);
        form.add(tfNoHp, 1, 3);
        form.add(new Label("Status"), 0, 4);
        form.add(cbStatus, 1, 4);

        tfNik.setPrefWidth(250);
        tfNama.setPrefWidth(250);
        tfAlamat.setPrefWidth(250);
        tfNoHp.setPrefWidth(250);

        // ── TOMBOL ──
        Button btnTambah = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnHapus = new Button("Hapus");
        Button btnClear = new Button("Clear");
        Button btnExportPdf = new Button("Export PDF");

        btnTambah.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnUpdate.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnHapus.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnClear.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white;");
        btnExportPdf.setStyle("-fx-background-color: #b91c1c; -fx-text-fill: white;");

        btnTambah.setPrefWidth(80);
        btnUpdate.setPrefWidth(80);
        btnHapus.setPrefWidth(80);
        btnClear.setPrefWidth(80);
        btnExportPdf.setPrefWidth(100);

        HBox tombol = new HBox(10, btnTambah, btnUpdate, btnHapus, btnClear, btnExportPdf);
        tombol.setPadding(new Insets(10, 0, 0, 0));

        btnExportPdf.setOnAction(e -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Simpan PDF");
            fileChooser.setInitialFileName("laporan_anggota.pdf");
            fileChooser.getExtensionFilters().add(
                new javafx.stage.FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );

            java.io.File file = fileChooser.showSaveDialog(btnExportPdf.getScene().getWindow());
            if (file != null) {
                try {
                    String filePath = file.getAbsolutePath();
                    if (!filePath.toLowerCase().endsWith(".pdf")) {
                        filePath += ".pdf";
                    }

                    List<Anggota> data = dao.getAll();
                    PdfExporter.exportAnggota(data, filePath);

                    javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION
                    );
                    alert.setTitle("Sukses");
                    alert.setContentText("PDF berhasil disimpan di:\n" + filePath);
                    alert.showAndWait();

                } catch (Exception ex) {
                    showAlert("Gagal simpan PDF: " + ex.getMessage());
                }
            }
        });


        // ── SEARCH ──
        tfCari.setPromptText("Cari nama atau NIK...");
        tfCari.setPrefWidth(250);
        Button btnCari = new Button("Cari");
        Button btnReset = new Button("Reset");
        HBox searchBox = new HBox(10, tfCari, btnCari, btnReset);
        searchBox.setPadding(new Insets(10));

        // ── AKSI TOMBOL ──
        btnTambah.setOnAction(e -> {
            try {
                if (tfNik.getText().isEmpty() || tfNama.getText().isEmpty()) {
                    showAlert("NIK dan Nama wajib diisi!");
                    return;
                }
                Anggota a = new Anggota(0, tfNik.getText(), tfNama.getText(),
                        tfAlamat.getText(), tfNoHp.getText(),
                        cbStatus.getValue(), java.time.LocalDate.now().toString());
                if (dao.tambah(a)) {
                    showAlert("Anggota berhasil ditambahkan!");
                    loadData();
                    clearForm();
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnUpdate.setOnAction(e -> {
            try {
                if (selectedId == -1) {
                    showAlert("Pilih data anggota dulu dari tabel!");
                    return;
                }
                Anggota a = new Anggota(selectedId, tfNik.getText(), tfNama.getText(),
                        tfAlamat.getText(), tfNoHp.getText(), cbStatus.getValue(), "");
                if (dao.update(a)) {
                    showAlert("Data berhasil diupdate!");
                    loadData();
                    clearForm();
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnHapus.setOnAction(e -> {
            try {
                if (selectedId == -1) {
                    showAlert("Pilih data anggota dulu dari tabel!");
                    return;
                }
                Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
                konfirmasi.setTitle("Konfirmasi Hapus");
                konfirmasi.setContentText("Yakin ingin menghapus anggota ini?");
                konfirmasi.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            if (dao.hapus(selectedId)) {
                                showAlert("Anggota berhasil dihapus!");
                                loadData();
                                clearForm();
                            }
                        } catch (Exception ex) {
                            showAlert("Error: " + ex.getMessage());
                        }
                    }
                });
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnClear.setOnAction(e -> clearForm());

        btnCari.setOnAction(e -> {
            try {
                String keyword = tfCari.getText();
                dataList.setAll(dao.cari(keyword));
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnReset.setOnAction(e -> {
            tfCari.clear();
            loadData();
        });

        // ── LAYOUT UTAMA ──
        Label judul = new Label("MODUL ANGGOTA - Koperasi Merah Putih");
        judul.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox formBox = new VBox(10, form, tombol);
        formBox.setPadding(new Insets(10));
        formBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5;");

        HBox headerBar = new HBox();
        headerBar.setPadding(new Insets(0, 0, 0, 0));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnKembaliDashboard = new Button("Kembali");
        btnKembaliDashboard.setStyle("-fx-background-color: #0862ea; -fx-text-fill: white;");
        btnKembaliDashboard.setPrefWidth(100);
        btnKembaliDashboard.setOnAction(e -> {
            try {
                stage.close();
                new DashboardView(username, role).start(new Stage());
            } catch (Exception ex) {
                showAlert("Gagal kembali ke dashboard: " + ex.getMessage());
            }
        });

        headerBar.getChildren().addAll(judul, spacer, btnKembaliDashboard);

        VBox root = new VBox(15, headerBar, searchBox, table, formBox);
        root.setPadding(new Insets(20));

        loadData();

        Scene scene = new Scene(root, 750, 600);
        stage.setTitle("Modul Anggota - Koperasi Merah Putih");
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {
        try {
            dataList.setAll(dao.getAll());
        } catch (Exception e) {
            showAlert("Gagal load data: " + e.getMessage());
        }
    }

    private void clearForm() {
        selectedId = -1;
        tfNik.clear();
        tfNama.clear();
        tfAlamat.clear();
        tfNoHp.clear();
        cbStatus.setValue("aktif");
        table.getSelectionModel().clearSelection();
    }

    private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}