package view;

import dao.SimpananDAO;
import dao.PinjamanDAO;
import model.Simpanan;
import model.Pinjaman;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SimpanPinjamView extends Application {

    private SimpananDAO simpananDAO = new SimpananDAO();
    private PinjamanDAO pinjamanDAO = new PinjamanDAO();
    private int selectedSimpananId = -1;
    private int selectedPinjamanId = -1;
    private String username;
    private String role;

    public SimpanPinjamView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public SimpanPinjamView() {
        this.username = "Admin";
        this.role = "admin";
    }

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabSimpanan = new Tab("Data Simpanan", createSimpananView());
        Tab tabPinjaman = new Tab("Data Pinjaman", createPinjamanView());

        tabPane.getTabs().addAll(tabSimpanan, tabPinjaman);

        Button btnKembali = new Button("Kembali");
        btnKembali.setStyle(
                "-fx-background-color: rgba(111, 190, 255, 0.87); -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnKembali.setOnAction(e -> {
            stage.close();
            try {
                new DashboardView("Admin", "admin").start(new Stage());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Gagal kembali ke Dashboard!");
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(spacer, btnKembali);
        topBar.setPadding(new Insets(10, 10, 0, 10));

        VBox root = new VBox(10, topBar, tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        Scene scene = new Scene(root, 900, 680);
        stage.setTitle("Modul Simpan Pinjam - Koperasi Merah Putih");
        stage.setScene(scene);
        stage.show();
    }

    // --- TAB SIMPANAN ---
    @SuppressWarnings("unchecked")
    private VBox createSimpananView() {
        TableView<Simpanan> table = new TableView<>();
        ObservableList<Simpanan> dataList = FXCollections.observableArrayList();

        TableColumn<Simpanan, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Simpanan, Integer> colIdAnggota = new TableColumn<>("ID Anggota");
        colIdAnggota.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        TableColumn<Simpanan, String> colJenis = new TableColumn<>("Jenis Simpanan");
        colJenis.setCellValueFactory(new PropertyValueFactory<>("jenisSimpanan"));
        TableColumn<Simpanan, Double> colJumlah = new TableColumn<>("Jumlah (Rp)");
        colJumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        TableColumn<Simpanan, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        TableColumn<Simpanan, String> colKet = new TableColumn<>("Keterangan");
        colKet.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        table.getColumns().addAll(colId, colIdAnggota, colJenis, colJumlah, colTanggal, colKet);
        table.setItems(dataList);

        TextField tfCari = new TextField();
        tfCari.setPromptText("Cari jenis / keterangan / ID Anggota...");
        tfCari.setPrefWidth(250);
        Button btnCari = new Button("Cari");
        HBox searchBox = new HBox(10, new Label("Pencarian"), tfCari, btnCari);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(5, 10, 5, 10));

        TextField tfIdAnggota = new TextField();
        ComboBox<String> cbJenis = new ComboBox<>();
        cbJenis.getItems().addAll("wajib", "sukarela"); // Sesuai DB Enum
        cbJenis.setValue("wajib");
        TextField tfJumlah = new TextField();
        TextField tfKet = new TextField();

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));
        form.add(new Label("ID Anggota"), 0, 0);
        form.add(tfIdAnggota, 1, 0);
        form.add(new Label("Jenis Simpanan"), 0, 1);
        form.add(cbJenis, 1, 1);
        form.add(new Label("Jumlah (Rp)"), 0, 2);
        form.add(tfJumlah, 1, 2);
        form.add(new Label("Keterangan"), 0, 3);
        form.add(tfKet, 1, 3);

        Button btnTambah = new Button("Tambah");
        Button btnRefresh = new Button("Refresh Data");
        Button btnUpdate = new Button("Update Data");
        Button btnDelete = new Button("Delete Data");
        HBox tombol = new HBox(15, btnTambah, btnRefresh, btnUpdate, btnDelete);
        tombol.setPadding(new Insets(15));
        btnDelete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");

        Runnable loadSimpanan = () -> {
            try {
                dataList.setAll(simpananDAO.getAll());
                selectedSimpananId = -1;
                tfIdAnggota.clear();
                tfJumlah.clear();
                tfKet.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        btnRefresh.setOnAction(e -> loadSimpanan.run());

        btnCari.setOnAction(e -> {
            try {
                dataList.setAll(simpananDAO.cari(tfCari.getText()));
            } catch (Exception ex) {
                showAlert("Gagal melakukan pencarian");
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedSimpananId = newSelection.getId();
                tfIdAnggota.setText(String.valueOf(newSelection.getIdAnggota()));
                cbJenis.setValue(newSelection.getJenisSimpanan());
                tfJumlah.setText(String.valueOf(newSelection.getJumlah()));
                tfKet.setText(newSelection.getKeterangan());
            }
        });

        btnUpdate.setOnAction(e -> {
            if (selectedSimpananId == -1) {
                showAlert("Pilih data di tabel terlebih dahulu!");
                return;
            }
            try {
                Simpanan s = new Simpanan(selectedSimpananId, Integer.parseInt(tfIdAnggota.getText()),
                        cbJenis.getValue(), Double.parseDouble(tfJumlah.getText()), "", tfKet.getText());
                simpananDAO.update(s);
                loadSimpanan.run();
            } catch (Exception ex) {
                showAlert("Gagal mengupdate data!");
            }
        });

        btnTambah.setOnAction(e -> {
            try {
                Simpanan s = new Simpanan(0, Integer.parseInt(tfIdAnggota.getText()),
                        cbJenis.getValue(), Double.parseDouble(tfJumlah.getText()),
                        java.time.LocalDate.now().toString(), tfKet.getText());
                simpananDAO.tambah(s);
                btnRefresh.fire();
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Cek input data simpanan!");
                a.show();
            }
        });

        btnDelete.setOnAction(e -> {
            if (selectedSimpananId == -1) {
                showAlert("Pilih data di tabel yang inggin di hapus");
                return;
            }
            try {
                simpananDAO.hapus(selectedSimpananId);
                loadSimpanan.run();
            } catch (Exception ex) {
                showAlert("Gagal menghapus data");
            }
        });

        btnRefresh.fire();
        return new VBox(15, searchBox, table, form, tombol);
    }

    // --- TAB PINJAMAN ---
    @SuppressWarnings("unchecked")
    private VBox createPinjamanView() {
        TableView<Pinjaman> table = new TableView<>();
        ObservableList<Pinjaman> dataList = FXCollections.observableArrayList();

        TableColumn<Pinjaman, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Pinjaman, String> colKode = new TableColumn<>("Kode pinjaman");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodePinjaman"));
        TableColumn<Pinjaman, Integer> colIdAnggota = new TableColumn<>("ID Anggota");
        colIdAnggota.setCellValueFactory(new PropertyValueFactory<>("idAnggota"));
        TableColumn<Pinjaman, Double> colJmlPinjam = new TableColumn<>("Jml Pinjam");
        colJmlPinjam.setCellValueFactory(new PropertyValueFactory<>("jumlahPinjam"));
        TableColumn<Pinjaman, Double> colJmlBayar = new TableColumn<>("Jml Bayar");
        colJmlBayar.setCellValueFactory(new PropertyValueFactory<>("jumlahBayar"));
        TableColumn<Pinjaman, Double> colCicilan = new TableColumn<>("Cicilan/Bulan");
        colCicilan.setCellValueFactory(new PropertyValueFactory<>("cicilanPerBulan"));
        TableColumn<Pinjaman, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<Pinjaman, String> colTanggal = new TableColumn<>("Tanggal");
        colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggalPinjam"));

        table.getColumns().addAll(colId, colKode, colIdAnggota, colJmlPinjam, colJmlBayar, colCicilan, colStatus,
                colTanggal);
        table.setItems(dataList);

        // --- UI PENCARIAN PINJAMAN ---
        TextField tfCari = new TextField();
        tfCari.setPromptText("Cari kode / status / ID Anggota...");
        tfCari.setPrefWidth(250);
        Button btnCari = new Button("Cari");
        HBox searchBox = new HBox(10, new Label("Pencarian:"), tfCari, btnCari);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(new Insets(5, 10, 5, 10));

        TextField tfIdAnggota = new TextField();
        TextField tfJmlPinjam = new TextField();
        TextField tfCicilan = new TextField();
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("aktif", "lunas"); // Sesuai DB Enum
        cbStatus.setValue("aktif");
        TextField tfBayar = new TextField();
        tfBayar.setPromptText("Masukan nominal bayar");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));
        form.add(new Label("ID Anggota"), 0, 0);
        form.add(tfIdAnggota, 1, 0);
        form.add(new Label("Jumlah Pinjam (Rp)"), 0, 1);
        form.add(tfJmlPinjam, 1, 1);
        form.add(new Label("Cicilan per Bulan (Rp)"), 0, 2);
        form.add(tfCicilan, 1, 2);
        form.add(new Label("Status"), 0, 3);
        form.add(cbStatus, 1, 3);
        form.add(new Label("Nominal Bayar Cicilan"), 0, 4);
        form.add(tfBayar, 1, 4);

        Button btnTambah = new Button("Tambah Pinjaman");
        Button btnRefres = new Button("Refresh Data");
        Button btnBayar = new Button("Bayar Setoran");
        Button btnUpdate = new Button("Update Status");
        Button btnDelete = new Button("Delete Pinjaman");
        btnDelete.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
        btnBayar.setStyle("-fx-background-color: #5cb85c; -fx-text-fill: white;");

        HBox tombol = new HBox(10, btnTambah, btnRefres, btnBayar, btnUpdate, btnDelete);
        tombol.setPadding(new Insets(10));

        Runnable LoadPinjaman = () -> {
            try {
                dataList.setAll(pinjamanDAO.getAll());
                selectedPinjamanId = -1;
                tfIdAnggota.clear();
                tfJmlPinjam.clear();
                tfCicilan.clear();
                tfBayar.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        btnRefres.setOnAction(e -> LoadPinjaman.run());

        btnCari.setOnAction(e -> {
            try {
                dataList.setAll(pinjamanDAO.cari(tfCari.getText()));
            } catch (Exception ex) {
                showAlert("Gagal melakukan pencarian data pinjaman!");
            }
        });

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPinjamanId = newSelection.getId();
                tfIdAnggota.setText(String.valueOf(newSelection.getIdAnggota()));
                tfJmlPinjam.setText(String.valueOf(newSelection.getJumlahPinjam()));
                tfCicilan.setText(String.valueOf(newSelection.getCicilanPerBulan()));
                cbStatus.setValue(newSelection.getStatus());
            }
        });

        btnTambah.setOnAction(e -> {
            try {
                String kodeOtomatis = pinjamanDAO.generateKodePinjaman();
                Pinjaman p = new Pinjaman(0, kodeOtomatis, Integer.parseInt(tfIdAnggota.getText()),
                        Double.parseDouble(tfJmlPinjam.getText()), 0.0,
                        Double.parseDouble(tfCicilan.getText()), cbStatus.getValue(),
                        java.time.LocalDate.now().toString());
                pinjamanDAO.tambah(p);
                LoadPinjaman.run();
            } catch (Exception ex) {
                System.out.println("Gagal minjam" + ex.getMessage());
                ex.printStackTrace();
                showAlert("Gagal menambah pinjaman!");
            }
        });

        btnBayar.setOnAction(e -> {
            if (selectedPinjamanId == -1) {
                showAlert("Pilih data pinjaman di tabel yang inggin di bayar!");
                return;
            }
            try {
                double nominalBayar = Double.parseDouble(tfBayar.getText());
                pinjamanDAO.bayarCicilan(selectedPinjamanId, nominalBayar);
                LoadPinjaman.run();
            } catch (Exception ex) {
                showAlert("Masukan nominal angka pembayaran");
            }
        });

        btnUpdate.setOnAction(e -> {
            if (selectedPinjamanId == -1) {
                showAlert("Pilih Data pinjaman di tabel terlebih dahulu!");
                return;
            }
            try {
                pinjamanDAO.updateStatus(selectedPinjamanId, cbStatus.getValue());
                LoadPinjaman.run();
            } catch (Exception ex) {
                showAlert("Gagal merubah status!");
            }
        });

        btnDelete.setOnAction(e -> {
            if (selectedPinjamanId == -1) {
                showAlert("Pilih data pinjaman yang inggin di hapus");
                return;
            }
            try {
                pinjamanDAO.hapus(selectedPinjamanId);
                LoadPinjaman.run();
            } catch (Exception ex) {
                showAlert("gagal menghapus data pinjaman");
            }
        });

        btnRefres.fire();
        return new VBox(15, searchBox, table, form, tombol);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
