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
import javafx.stage.Stage;

public class SimpanPinjamView extends Application {

    private SimpananDAO simpananDAO = new SimpananDAO();
    private PinjamanDAO pinjamanDAO = new PinjamanDAO();

    @Override
    public void start(Stage stage) {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabSimpanan = new Tab("Data Simpanan", createSimpananView());
        Tab tabPinjaman = new Tab("Data Pinjaman", createPinjamanView());

        tabPane.getTabs().addAll(tabSimpanan, tabPinjaman);

        VBox root = new VBox(10, tabPane);
        Scene scene = new Scene(root, 800, 650);
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

        TextField tfIdAnggota = new TextField();
        ComboBox<String> cbJenis = new ComboBox<>();
        cbJenis.getItems().addAll("pokok", "wajib", "sukarela"); // Sesuai DB Enum
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
        Button btnMuat = new Button("Refresh Data");
        HBox tombol = new HBox(10, btnTambah, btnMuat);
        tombol.setPadding(new Insets(10));

        btnMuat.setOnAction(e -> {
            try {
                dataList.setAll(simpananDAO.getAll());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnTambah.setOnAction(e -> {
            try {
                Simpanan s = new Simpanan(0, Integer.parseInt(tfIdAnggota.getText()),
                        cbJenis.getValue(), Double.parseDouble(tfJumlah.getText()),
                        java.time.LocalDate.now().toString(), tfKet.getText());
                simpananDAO.tambah(s);
                btnMuat.fire();
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Cek input data simpanan!");
                a.show();
            }
        });

        btnMuat.fire();
        return new VBox(15, table, form, tombol);
    }

    // --- TAB PINJAMAN ---
    @SuppressWarnings("unchecked")
    private VBox createPinjamanView() {
        TableView<Pinjaman> table = new TableView<>();
        ObservableList<Pinjaman> dataList = FXCollections.observableArrayList();

        TableColumn<Pinjaman, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Pinjaman, String> colKode = new TableColumn<>("Kode");
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

        TextField tfKode = new TextField();
        TextField tfIdAnggota = new TextField();
        TextField tfJmlPinjam = new TextField();
        TextField tfCicilan = new TextField();
        ComboBox<String> cbStatus = new ComboBox<>();
        cbStatus.getItems().addAll("aktif", "lunas"); // Sesuai DB Enum
        cbStatus.setValue("aktif");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));
        form.add(new Label("Kode Pinjaman"), 0, 0);
        form.add(tfKode, 1, 0);
        form.add(new Label("ID Anggota"), 0, 1);
        form.add(tfIdAnggota, 1, 1);
        form.add(new Label("Jumlah Pinjam (Rp)"), 0, 2);
        form.add(tfJmlPinjam, 1, 2);
        form.add(new Label("Cicilan per Bulan (Rp)"), 0, 3);
        form.add(tfCicilan, 1, 3);
        form.add(new Label("Status"), 0, 4);
        form.add(cbStatus, 1, 4);

        Button btnTambah = new Button("Tambah Pinjaman");
        Button btnMuat = new Button("Refresh Data");
        HBox tombol = new HBox(10, btnTambah, btnMuat);
        tombol.setPadding(new Insets(10));

        btnMuat.setOnAction(e -> {
            try {
                dataList.setAll(pinjamanDAO.getAll());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnTambah.setOnAction(e -> {
            try {
                // Asumsi jumlah bayar awal adalah 0
                Pinjaman p = new Pinjaman(0, tfKode.getText(), Integer.parseInt(tfIdAnggota.getText()),
                        Double.parseDouble(tfJmlPinjam.getText()), 0.0,
                        Double.parseDouble(tfCicilan.getText()), cbStatus.getValue(),
                        java.time.LocalDate.now().toString());
                pinjamanDAO.tambah(p);
                btnMuat.fire();
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Cek input data pinjaman!");
                a.show();
            }
        });

        btnMuat.fire();
        return new VBox(15, table, form, tombol);
    }

    public static void main(String[] args) {
        launch(args);
    }
}