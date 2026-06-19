package view;

import java.util.List; // Untuk memakai List untuk menyimpan data anggota.

import dao.AnggotaDAO; // DAO untuk mengakses data anggota dari database.
import javafx.application.Application; // Kelas dasar untuk aplikasi JavaFX.
import javafx.collections.FXCollections; // Untuk membuat list yang bisa langsung terhubung ke tabel.
import javafx.collections.ObservableList; // List yang otomatis memberi notifikasi jika ada perubahan data.
import javafx.geometry.Insets; // Untuk mengatur jarak di layout.
import javafx.scene.Scene; // Objek tampilan utama jendela.
import javafx.scene.control.Alert; // Kotak pesan informasi.
import javafx.scene.control.Button; // Tombol.
import javafx.scene.control.ButtonType; // Jenis tombol pada dialog konfirmasi.
import javafx.scene.control.ComboBox; // Kotak pilihan.
import javafx.scene.control.Label; // Teks label.
import javafx.scene.control.TableColumn; // Kolom tabel.
import javafx.scene.control.TableView; // Tabel.
import javafx.scene.control.TextField; // Input teks.
import javafx.scene.control.cell.PropertyValueFactory; // Menghubungkan kolom tabel dengan properti objek.
import javafx.scene.layout.GridPane; // Layout bentuk grid untuk form.
import javafx.scene.layout.HBox; // Layout horizontal.
import javafx.scene.layout.Priority; // Untuk mengatur pembagian ruang.
import javafx.scene.layout.Region; // Komponen kosong untuk memberi jarak.
import javafx.scene.layout.VBox; // Layout vertical.
import javafx.stage.FileChooser; // Dialog untuk memilih lokasi file PDF.
import javafx.stage.Stage; // Jendela aplikasi.
import library.pdf.PdfExporter; // Class untuk export data ke PDF.
import model.Anggota; // Model data anggota.

public class AnggotaView extends Application {

    // DAO (Data Access Object) untuk menghubungkan tampilan dengan database.
    private final AnggotaDAO dao = new AnggotaDAO();

    // Tabel yang menampilkan daftar anggota dan data yang akan ditampilkan di tabel.
    private final TableView<Anggota> table = new TableView<>();
    private final ObservableList<Anggota> dataList = FXCollections.observableArrayList();

    // Menyimpan informasi user yang sedang login agar bisa kembali ke dashboard.
    private final String username;
    private final String role;

    // Field input untuk form anggota.
    private final TextField tfNik = new TextField();
    private final TextField tfNama = new TextField();
    private final TextField tfAlamat = new TextField();
    private final TextField tfNoHp = new TextField();
    private final ComboBox<String> cbStatus = new ComboBox<>();
    private final TextField tfCari = new TextField();

    // Variabel untuk menyimpan ID anggota yang sedang dipilih di tabel.
    private int selectedId = -1;

    // Konstruktor default yang dipakai jika objek dibuat tanpa parameter.
    public AnggotaView() {
        this("", "");
    }

    // Konstruktor yang menerima username dan role untuk navigasi kembali ke dashboard.
    public AnggotaView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // Method utama yang membuat tampilan dan menghubungkan semua aksi.
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) throws Exception {

        // Bagian judul dan tombol kembali ke dashboard.
        // Label digunakan untuk menampilkan teks judul di bagian atas layar.
        Label judul = new Label("MODUL ANGGOTA - Koperasi Merah Putih");
        judul.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Tombol untuk kembali ke dashboard.
        // Saat tombol ditekan, jendela saat ini ditutup lalu dashboard dibuka kembali.
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

        // Region digunakan sebagai spacer agar tombol kembali berada di kanan.
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox headerBar = new HBox(judul, spacer, btnKembaliDashboard);
        headerBar.setPadding(new Insets(0, 0, 5, 0));

        // Bagian pencarian.
        // TextField ini dipakai untuk mengetik kata kunci pencarian.
        tfCari.setPromptText("Cari nama atau NIK...");
        tfCari.setPrefWidth(280);

        // Tombol cari untuk menjalankan pencarian berdasarkan input user.
        Button btnCari = new Button("Cari");
        HBox searchBox = new HBox(10, tfCari, btnCari);
        searchBox.setPadding(new Insets(0, 0, 10, 0));

        // Pengaturan kolom tabel agar tampil lebih simpel dan fokus ke data penting.
        // TableColumn menghubungkan tiap kolom dengan properti objek Anggota.
        TableColumn<Anggota, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(45);

        TableColumn<Anggota, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(170);

        TableColumn<Anggota, String> colNik = new TableColumn<>("NIK");
        colNik.setCellValueFactory(new PropertyValueFactory<>("nik"));
        colNik.setPrefWidth(130);

        TableColumn<Anggota, String> colNoHp = new TableColumn<>("No HP");
        colNoHp.setCellValueFactory(new PropertyValueFactory<>("noHp"));
        colNoHp.setPrefWidth(110);

        TableColumn<Anggota, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(80);

        // Menambahkan kolom ke tabel dan menghubungkan dataList agar tabel tampil.
        table.getColumns().addAll(colId, colNama, colNik, colNoHp, colStatus);
        table.setItems(dataList);
        table.setPrefHeight(260);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Listener ini berjalan saat user memilih salah satu baris di tabel.
        // Data dari baris yang dipilih akan otomatis muncul di form.
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

        // Form input untuk menambah atau mengubah data anggota.
        // Prompt text adalah petunjuk yang muncul di dalam input.
        tfNik.setPromptText("Masukkan NIK (16 digit)");
        tfNama.setPromptText("Masukkan Nama Lengkap");
        tfAlamat.setPromptText("Masukkan Alamat");
        tfNoHp.setPromptText("Masukkan No HP");
        cbStatus.getItems().addAll("aktif", "nonaktif");
        cbStatus.setValue("aktif");

        // GridPane digunakan untuk menyusun form dalam bentuk baris dan kolom.
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(8);
        form.setPadding(new Insets(10));

        // Menambahkan label dan input ke grid.
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

        // Memberi lebar tetap agar form terlihat rapi.
        tfNik.setPrefWidth(250);
        tfNama.setPrefWidth(250);
        tfAlamat.setPrefWidth(250);
        tfNoHp.setPrefWidth(250);

        // Tombol aksi utama untuk modul anggota.
        Button btnTambah = new Button("Tambah");
        Button btnUpdate = new Button("Update");
        Button btnHapus = new Button("Hapus");
        Button btnClear = new Button("Clear");
        Button btnExportPdf = new Button("Export PDF");

        // Style tombol: hanya Delete dan Back yang punya warna khusus.
        // Tombol lainnya pakai warna default sistem.
        btnHapus.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        // HBox untuk menyusun tombol-tombol aksi secara horizontal.
        HBox tombol = new HBox(10, btnTambah, btnUpdate, btnHapus, btnClear, btnExportPdf);
        tombol.setPadding(new Insets(10, 0, 0, 0));

        // Aksi tombol clear untuk membersihkan form dan pilihan tabel.
        btnClear.setOnAction(e -> clearForm());

        // Aksi tombol export PDF untuk menyimpan laporan anggota.
        // FileChooser dipakai agar user bisa memilih lokasi file hasil export.
        btnExportPdf.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Simpan PDF");
            fileChooser.setInitialFileName("laporan_anggota.pdf");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
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

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sukses");
                    alert.setContentText("PDF berhasil disimpan di:\n" + filePath);
                    alert.showAndWait();
                } catch (Exception ex) {
                    showAlert("Gagal simpan PDF: " + ex.getMessage());
                }
            }
        });

        // Aksi tombol cari untuk memfilter data berdasarkan nama atau NIK.
        // Jika input kosong, program menampilkan semua data lagi.
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

        // Aksi tombol tambah untuk menyimpan anggota baru.
        // Validasi sederhana dipakai supaya NIK dan Nama tidak boleh kosong.
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
                        java.time.LocalDate.now().toString()
                );

                if (dao.tambah(anggota)) {
                    showAlert("Anggota berhasil ditambahkan!");
                    loadData();
                    clearForm();
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        // Aksi tombol update untuk memperbarui data yang sudah dipilih.
        // Program memastikan user sudah memilih data terlebih dahulu.
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
                        ""
                );

                if (dao.update(anggota)) {
                    showAlert("Data berhasil diupdate!");
                    loadData();
                    clearForm();
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        // Aksi tombol hapus dengan konfirmasi sebelum benar-benar dihapus.
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

        // Layout utama yang menyatukan header, pencarian, tabel, dan form.
        VBox formBox = new VBox(10, form, tombol);
        formBox.setPadding(new Insets(10));
        formBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5;");

        // Root layout utama yang akan dibangun sebagai scene.
        VBox root = new VBox(15, headerBar, searchBox, table, formBox);
        root.setPadding(new Insets(20));

        // Memanggil method untuk menampilkan data awal saat layar dibuka.
        loadData();

        Scene scene = new Scene(root, 800, 620);
        stage.setTitle("Modul Anggota - Koperasi Merah Putih");
        stage.setScene(scene);
        stage.show();
    }

    // Method untuk memuat ulang seluruh data anggota dari database.
    private void loadData() {
        try {
            dataList.setAll(dao.getAll());
        } catch (Exception e) {
            showAlert("Gagal load data: " + e.getMessage());
        }
    }

    // Method untuk membersihkan form dan menghapus pilihan tabel.
    private void clearForm() {
        selectedId = -1;
        tfNik.clear();
        tfNama.clear();
        tfAlamat.clear();
        tfNoHp.clear();
        cbStatus.setValue("aktif");
        table.getSelectionModel().clearSelection();
    }

    // Method untuk menampilkan pesan informasi kepada pengguna.
    private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    // Method utama yang dijalankan saat aplikasi dibuka.
    public static void main(String[] args) {
        launch(args);
    }
}