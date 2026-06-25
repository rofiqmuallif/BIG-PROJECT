package view;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DashboardView {

    private final String username;
    private final String role;

    public DashboardView(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public void start(Stage stage) {
        String normalizedRole = role == null ? "" : role.trim().toLowerCase();
        boolean isAdmin = "admin".equals(normalizedRole);

        Label lblJudul = new Label("Koperasi Merah Putih");
        lblJudul.setFont(Font.font("Arial", 22));
        lblJudul.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSub = new Label("Sistem Informasi Koperasi");
        lblSub.setStyle("-fx-text-fill: #ffcccc;");

        Label lblUser = new Label("Login sebagai: " + username + " (" + role + ")");
        lblUser.setStyle("-fx-text-fill: #ffcccc; -fx-font-size: 11px;");

        Button btnLogout = new Button("Logout");
        btnLogout.setStyle("-fx-background-color: white; -fx-text-fill: #b71c1c; " +
                "-fx-font-weight: bold;");

        btnLogout.setOnAction(e -> {
            stage.close();
            try {
                new LoginView().start(new Stage());
            } catch (Exception ex) {
                showAlert("Gagal kembali ke login: " + ex.getMessage());
            }
        });

        VBox judulBox = new VBox(3, lblJudul, lblSub, lblUser);
        judulBox.setAlignment(Pos.CENTER_LEFT);

        HBox header = new HBox(judulBox, btnLogout);

        HBox.setHgrow(judulBox, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 30, 20, 30));
        header.setStyle("-fx-background-color: #b71c1c;");

        Button btnAnggota = buatCard("👤", "Kelola Anggota",
                "Kelola data anggota koperasi", "#1565C0");
        Button btnProduk = buatCard("📦", "Kelola Produk & Stok",
                "Kelola produk dan stok barang", "#2E7D32");
        Button btnTransaksi = buatCard("💳", "Kelola Transaksi",
                "Catat transaksi pembelian", "#E65100");
        Button btnSimpanPinjam = buatCard("🏦", "Kelola Simpan Pinjam",
                "Kelola simpanan dan pinjaman", "#6A1B9A");

        btnAnggota.setOnAction(e ->
            buka(stage, new AnggotaView(username, role)));

        btnProduk.setOnAction(e ->
            buka(stage, new ProdukView(username, role)));
        
        btnTransaksi.setOnAction(e ->
            buka(stage, new TransaksiView(username, role)));

        btnSimpanPinjam.setOnAction(e ->
            buka(stage, new SimpanPinjamView(username, role)));

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        grid.add(btnAnggota, 0, 0);
        grid.add(btnTransaksi, 0, 1);

        if (isAdmin) {
            grid.add(btnProduk, 1, 0);
            grid.add(btnSimpanPinjam, 1, 1);
        }

        Label footer = new Label("© 2026 Koperasi Merah Putih - Sistem Informasi");
        footer.setStyle("-fx-text-fill: #999; -fx-font-size: 11px;");
        HBox footerBox = new HBox(footer);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(10));

        VBox root = new VBox(header, grid, footerBox);
        VBox.setVgrow(grid, Priority.ALWAYS);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Scene scene = new Scene(root, 650, 500);
        stage.setTitle("Dashboard - Koperasi Merah Putih");
        stage.setScene(scene);
        stage.show();
    }

    private Button buatCard(String icon, String judul, String deskripsi, String warna) {
        Label lblIcon = new Label(icon);
        lblIcon.setFont(Font.font(36));

        Label lblJudul = new Label(judul);
        lblJudul.setFont(Font.font("Arial", 14));
        lblJudul.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        Label lblDesk = new Label(deskripsi);
        lblDesk.setStyle("-fx-text-fill: #ffffffcc; -fx-font-size: 11px;");
        lblDesk.setWrapText(true);

        VBox card = new VBox(8, lblIcon, lblJudul, lblDesk);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));
        card.setPrefSize(230, 160);
        card.setStyle("-fx-background-color: " + warna + "; " +
                "-fx-background-radius: 10; -fx-cursor: hand;");

        Button btn = new Button();
        btn.setGraphic(card);
        btn.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        tambahHover(btn, card, warna);
        return btn;
    }

    private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }

    private void buka(Stage stage, Application app) {
        try {
            stage.close();
            app.start(new Stage());
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void tambahHover(Button btn, VBox card, String warna) {
        btn.setOnMouseEntered(e ->
            card.setStyle("-fx-background-color: derive(" + warna + ", -15%);" +
                        "-fx-background-radius: 10; -fx-cursor: hand;"));

        btn.setOnMouseExited(e ->
            card.setStyle("-fx-background-color: " + warna + ";" +
                        "-fx-background-radius: 10; -fx-cursor: hand;"));
    }


}
