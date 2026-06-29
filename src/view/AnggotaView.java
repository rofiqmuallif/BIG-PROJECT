package view;

import dao.AnggotaDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Anggota;

public class AnggotaView extends Application {

    private final AnggotaDAO dao = new AnggotaDAO();

    private final TableView<Anggota> table = new TableView<>();
    private final ObservableList<Anggota> dataList = FXCollections.observableArrayList();

    private final String username;
    private final String role;

    private final TextField tfNik = new TextField();
    private final TextField tfNama = new TextField();
    private final TextField tfAlamat = new TextField();
    private final TextField tfNoHp = new TextField();
    private final ComboBox<String> cbStatus = new ComboBox<>();
    private final TextField tfCari = new TextField();

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

        Label judul = new Label("KELOLA ANGGOTA - Koperasi Merah Putih");
        judul.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

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

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox headerBar = new HBox(judul, spacer, btnKembaliDashboard);
        headerBar.setPadding(new Insets(0, 0, 5, 0));

        tfCari.setPromptText("Cari nama atau NIK...");
        tfCari.setPrefWidth(280);

        Button btnCari = new Button("Cari");
        HBox searchBox = new HBox(10, tfCari, btnCari);
        searchBox.setPadding(new Insets(0, 0, 10, 0));

        table.getColumns().addAll(
                buatKolom("ID", "id", 45),
                buatKolom("Nama", "nama", 170),
                buatKolom("NIK", "nik", 130),
                buatKolom("No HP", "noHp", 110),
                buatKolom("Status", "status", 80));

        table.setItems(dataList);
        table.setPrefHeight(260);

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

        for (TextField tf : new TextField[] {
                tfNik, tfNama, tfAlamat, tfNoHp
        }) {
            tf.setPrefWidth(250);
        }

        Button btnTambah = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnHapus = new Button("Hapus");
        Button btnClear = new Button("Clear");

        btnHapus.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        HBox tombol = new HBox(10, btnTambah, btnUpdate, btnHapus, btnClear);
        tombol.setPadding(new Insets(10, 0, 0, 0));

        btnClear.setOnAction(e -> clearForm());

        btnCari.setOnAction(e -> {
            try {
                String keyword = tfCari.getText().trim();
                if (keyword.isEmpty()) {
                    loadData();
                } else {
                    dataList.setAll(dao.cari(keyword));
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnTambah.setOnAction(e -> {
            try {
                if (tfNik.getText().isEmpty() || tfNama.getText().isEmpty()) {
                    showAlert("NIK dan Nama wajib diisi!");
                    return;
                }

                Anggota anggota = new Anggota(
                        0,
                        tfNik.getText(),
                        tfNama.getText(),
                        tfAlamat.getText(),
                        tfNoHp.getText(),
                        cbStatus.getValue(),
                        java.time.LocalDate.now().toString());

                if (dao.tambah(anggota)) {
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

                Anggota anggota = new Anggota(
                        selectedId,
                        tfNik.getText(),
                        tfNama.getText(),
                        tfAlamat.getText(),
                        tfNoHp.getText(),
                        cbStatus.getValue(),
                        "");

                if (dao.update(anggota)) {
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

        VBox formBox = new VBox(10, form, tombol);
        formBox.setPadding(new Insets(10));
        formBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5;");

        VBox root = new VBox(15, headerBar, searchBox, table, formBox);
        root.setPadding(new Insets(20));

        loadData();

        Scene scene = new Scene(root, 800, 620);
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

    private <T> TableColumn<Anggota, T> buatKolom(String judul, String properti, int lebar) {
        TableColumn<Anggota, T> kolom = new TableColumn<>(judul);
        kolom.setCellValueFactory(new PropertyValueFactory<>(properti));
        kolom.setPrefWidth(lebar);
        return kolom;
    }

    public static void main(String[] args) {
        launch(args);
    }
}