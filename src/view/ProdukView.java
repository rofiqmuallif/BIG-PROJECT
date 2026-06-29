package view;

import dao.ProdukDAO;
import model.Produk;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.Optional;

public class ProdukView extends Application {

    private ProdukDAO produkDAO = new ProdukDAO();
    private ObservableList<Produk> produkList = FXCollections.observableArrayList();
    private TableView<Produk> table = new TableView<>();

    private String username;
    private String role;

    private TextField txtKode   = new TextField();
    private TextField txtNama   = new TextField();
    private TextField txtHarga  = new TextField();
    private TextField txtStok   = new TextField();
    private TextField txtSatuan = new TextField();
    private TextField txtCari   = new TextField();

    private Button btnTambah  = new Button("+ Tambah");
    private Button btnUpdate  = new Button("Edit Update");
    private Button btnHapus   = new Button("🗑 Hapus");
    private Button btnBersih  = new Button("Bersihkan");
    private Button btnKembali = new Button("Kembali"); // Text diubah menjadi "Kembali" tanpa panah
    private Label  lblStatus  = new Label("Selamat datang!");

    private Produk selectedProduk = null;

    public ProdukView() {
        this("", "");
    }

    public ProdukView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Manajemen Produk - Koperasi Merah Putih");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F0F4F8;");

        root.setTop(buildHeader());
        root.setCenter(buildCenter());
        root.setBottom(buildStatusBar());

        loadData();

        Scene scene = new Scene(root, 1000, 680);
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(640);
        stage.show();
    }

    private HBox buildHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(18, 24, 18, 24));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: #C0392B;");
        
        VBox titles = new VBox(2);
        Label title = new Label("MODUL PRODUK - Koperasi Merah Putih");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label sub = new Label("Koperasi Merah Putih");
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: #FFCDD2;");
        titles.getChildren().addAll(title, sub);
 
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnKembali.setStyle("-fx-background-color: #1A73E8; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 20;");
        btnKembali.setMaxWidth(Button.USE_COMPUTED_SIZE);
 
        btnKembali.setOnAction(e -> {
            try {
                DashboardView dashboard = new DashboardView("Admin", "admin");
                Stage mainStage = new Stage();
                dashboard.start(mainStage);
                
                ((Stage) btnKembali.getScene().getWindow()).close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        header.getChildren().addAll(titles, spacer, btnKembali);
        return header;
    }

    private HBox buildCenter() {
        HBox center = new HBox(16);
        center.setPadding(new Insets(16));

        VBox tableSection = buildTableSection();
        VBox formContent  = buildFormSection();

        ScrollPane formScroll = new ScrollPane(formContent);
        formScroll.setFitToWidth(true);
        formScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        formScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        formScroll.setPrefWidth(270);
        formScroll.setMinWidth(250);
        formScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        HBox.setHgrow(tableSection, Priority.ALWAYS);
        center.getChildren().addAll(tableSection, formScroll);
        return center;
    }

    @SuppressWarnings("unchecked")
    private VBox buildTableSection() {
        VBox box = new VBox(10);
 
        HBox searchBar = new HBox(8);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        txtCari.setPromptText("Cari nama / kode produk...");
        txtCari.setPrefWidth(280);
        txtCari.setStyle(fieldStyle());

        Button btnCari    = new Button("Cari");
        Button btnRefresh = new Button("Refresh");
        btnCari.setStyle(btnSecStyle());
        btnRefresh.setStyle(btnSecStyle());

        btnCari.setOnAction(e -> cariProduk());
        btnRefresh.setOnAction(e -> { txtCari.clear(); loadData(); });
        txtCari.setOnAction(e -> cariProduk());

        searchBar.getChildren().addAll(txtCari, btnCari, btnRefresh);
 
        TableColumn<Produk, Integer> colId   = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(45);

        TableColumn<Produk, String> colKode  = new TableColumn<>("Kode");
        colKode.setCellValueFactory(new PropertyValueFactory<>("kodeProduk"));
        colKode.setPrefWidth(90);

        TableColumn<Produk, String> colNama  = new TableColumn<>("Nama Produk");
        colNama.setCellValueFactory(new PropertyValueFactory<>("namaProduk"));
        colNama.setPrefWidth(180);

        TableColumn<Produk, Double> colHarga = new TableColumn<>("Harga (Rp)");
        colHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        colHarga.setPrefWidth(110);
        colHarga.setCellFactory(col -> new TableCell<>() {
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("Rp %,.0f", item));
            }
        });

        TableColumn<Produk, Integer> colStok = new TableColumn<>("Stok");
        colStok.setCellValueFactory(new PropertyValueFactory<>("stok"));
        colStok.setPrefWidth(65);

        TableColumn<Produk, String> colSat   = new TableColumn<>("Satuan");
        colSat.setCellValueFactory(new PropertyValueFactory<>("satuan"));
        colSat.setPrefWidth(80);

        table.getColumns().addAll(colId, colKode, colNama, colHarga, colStok, colSat);
        table.setItems(produkList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setStyle("-fx-background-color: white;");
        VBox.setVgrow(table, Priority.ALWAYS);

        table.getSelectionModel().selectedItemProperty().addListener((obs, o, newVal) -> {
            if (newVal != null) isiForm(newVal);
        });

        Label lblJumlah = new Label();
        produkList.addListener((ListChangeListener<Produk>) c ->
                lblJumlah.setText("Total: " + produkList.size() + " produk"));
        lblJumlah.setStyle("-fx-text-fill: #757575; -fx-font-size: 12px;");

        box.getChildren().addAll(searchBar, table, lblJumlah);
        return box;
    }

    private VBox buildFormSection() {
        VBox box = new VBox(8);
        box.setPrefWidth(260);
        box.setMinWidth(240);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: white; -fx-border-color: #E0E0E0; "
                + "-fx-border-radius: 10; -fx-background-radius: 10;");

        Label formTitle = new Label("Data Produk");
        formTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #C0392B;");

        txtKode.setPromptText("Kode Produk");
        txtNama.setPromptText("Nama Produk");
        txtHarga.setPromptText("Harga");
        txtStok.setPromptText("Stok");
        txtSatuan.setPromptText("Satuan (kg, ltr, pcs...)");

        for (TextField tf : new TextField[]{txtKode, txtNama, txtHarga, txtStok, txtSatuan}) {
            tf.setStyle(fieldStyle());
            tf.setMaxWidth(Double.MAX_VALUE);
        }

        btnTambah.setStyle(btnPrimaryStyle());
        btnUpdate.setStyle(btnWarningStyle());
        btnHapus.setStyle(btnDangerStyle());
        btnBersih.setStyle(btnSecStyle());

        for (Button b : new Button[]{btnTambah, btnUpdate, btnHapus, btnBersih}) {
            b.setMaxWidth(Double.MAX_VALUE);
        }

        btnTambah.setOnAction(e -> tambahProduk());
        btnUpdate.setOnAction(e -> updateProduk());
        btnHapus.setOnAction(e -> hapusProduk());
        btnBersih.setOnAction(e -> bersihkanForm());

        box.getChildren().addAll(
                formTitle, new Separator(),
                labelFor("Kode Produk"), txtKode,
                labelFor("Nama Produk"), txtNama,
                labelFor("Harga (Rp)"), txtHarga,
                labelFor("Stok"), txtStok,
                labelFor("Satuan"), txtSatuan,
                new Separator(),
                btnTambah, btnUpdate, btnHapus, btnBersih
        );
        return box;
    }

    private HBox buildStatusBar() {
        HBox bar = new HBox();
        bar.setPadding(new Insets(8, 16, 8, 16));
        bar.setStyle("-fx-background-color: #ECEFF1; -fx-border-color: #CFD8DC; -fx-border-width: 1 0 0 0;");
        lblStatus.setStyle("-fx-text-fill: #546E7A; -fx-font-size: 12px;");
        bar.getChildren().add(lblStatus);
        return bar;
    }
 

    private void loadData() {
        produkList.clear();
        produkList.addAll(produkDAO.getAllProduk());
        setStatus("Data dimuat. Total: " + produkList.size() + " produk.");
    }

    private void tambahProduk() {
        if (!validasiForm()) return;
        if (produkDAO.getProdukByKode(txtKode.getText().trim()) != null) {
            showAlert(Alert.AlertType.WARNING, "Kode produk sudah digunakan!");
            return;
        }
        Produk p = new Produk(
                txtKode.getText().trim(), txtNama.getText().trim(),
                Double.parseDouble(txtHarga.getText().trim()),
                Integer.parseInt(txtStok.getText().trim()),
                txtSatuan.getText().trim()
        );
        if (produkDAO.tambahProduk(p)) { loadData(); bersihkanForm(); setStatus("Produk berhasil ditambahkan!"); }
        else showAlert(Alert.AlertType.ERROR, "Gagal menambahkan produk.");
    }

    private void updateProduk() {
        if (selectedProduk == null) { showAlert(Alert.AlertType.WARNING, "Pilih produk di tabel dulu!"); return; }
        if (!validasiForm()) return;
        selectedProduk.setKodeProduk(txtKode.getText().trim());
        selectedProduk.setNamaProduk(txtNama.getText().trim());
        selectedProduk.setHarga(Double.parseDouble(txtHarga.getText().trim()));
        selectedProduk.setStok(Integer.parseInt(txtStok.getText().trim()));
        selectedProduk.setSatuan(txtSatuan.getText().trim());
        if (produkDAO.updateProduk(selectedProduk)) { loadData(); bersihkanForm(); setStatus("Produk berhasil diupdate!"); }
        else showAlert(Alert.AlertType.ERROR, "Gagal update produk.");
    }

    private void hapusProduk() {
        if (selectedProduk == null) { showAlert(Alert.AlertType.WARNING, "Pilih produk di tabel dulu!"); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Konfirmasi Hapus");
        confirm.setHeaderText("Hapus Produk");
        confirm.setContentText("Yakin ingin menghapus produk:\n\""
                + selectedProduk.getNamaProduk() + "\" (" + selectedProduk.getKodeProduk() + ")?\n\nData tidak bisa dikembalikan!");
        Optional<ButtonType> r = confirm.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            if (produkDAO.hapusProduk(selectedProduk.getId())) {
                setStatus("Produk \"" + selectedProduk.getNamaProduk() + "\" berhasil dihapus.");
                loadData();
                bersihkanForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal hapus produk.");
            }
        }
    }

    private void cariProduk() {
        String kw = txtCari.getText().trim().toLowerCase();
        if (kw.isEmpty()) { loadData(); return; }
        produkList.clear();
        for (Produk p : produkDAO.getAllProduk()) {
            if (p.getNamaProduk().toLowerCase().contains(kw) || p.getKodeProduk().toLowerCase().contains(kw))
                produkList.add(p);
        }
        setStatus("Hasil pencarian '" + kw + "': " + produkList.size() + " produk.");
    }
 

    private void isiForm(Produk p) {
        selectedProduk = p;
        txtKode.setText(p.getKodeProduk());
        txtNama.setText(p.getNamaProduk());
        txtHarga.setText(String.valueOf((int) p.getHarga()));
        txtStok.setText(String.valueOf(p.getStok()));
        txtSatuan.setText(p.getSatuan());
        setStatus("Produk dipilih: " + p.getNamaProduk());
    }

    private void bersihkanForm() {
        selectedProduk = null;
        txtKode.clear(); txtNama.clear(); txtHarga.clear(); txtStok.clear(); txtSatuan.clear();
        table.getSelectionModel().clearSelection();
    }

    private boolean validasiForm() {
        if (txtKode.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty()
                || txtHarga.getText().trim().isEmpty() || txtStok.getText().trim().isEmpty()
                || txtSatuan.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Semua field harus diisi!"); return false;
        }
        try { Double.parseDouble(txtHarga.getText().trim()); } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Harga harus angka!"); return false; }
        try { Integer.parseInt(txtStok.getText().trim()); } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Stok harus angka bulat!"); return false; }
        return true;
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg, ButtonType.OK) {{ setHeaderText(null); }}.showAndWait();
    }

    private void setStatus(String msg) { lblStatus.setText(msg); }

    private Label labelFor(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-font-size: 11px; -fx-text-fill: #757575;");
        return l;
    }

    private String fieldStyle() {
        return "-fx-background-color: #F5F5F5; -fx-border-color: #E0E0E0; "
                + "-fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 7 10; -fx-font-size: 13px;";
    }
    private String btnPrimaryStyle() {
        return "-fx-background-color: #C0392B; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 0;";
    }
    private String btnWarningStyle() {
        return "-fx-background-color: #E67E22; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 0;";
    }
    private String btnDangerStyle() {
        return "-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-weight: bold; "
                + "-fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 8 0;";
    }
    private String btnSecStyle() {
        return "-fx-background-color: #ECF0F1; -fx-text-fill: #2C3E50; -fx-font-weight: bold; "
                + "-fx-background-radius: 6; -fx-cursor: hand; -fx-padding: 7 12;";
    }

    public static void main(String[] args) { launch(args); }
}