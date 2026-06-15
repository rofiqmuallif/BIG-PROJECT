package view;

import dao.UserDAO;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegisterView {

    private UserDAO userDAO = new UserDAO();

    public void start(Stage stage) {

        // ── JUDUL ──
        Label lblJudul = new Label("Daftar Akun Baru");
        lblJudul.setFont(Font.font("Arial", 18));
        lblJudul.setStyle("-fx-font-weight: bold; -fx-text-fill: #b71c1c;");

        // ── FORM ──
        Label lblUsername = new Label("Username");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Buat username");
        tfUsername.setPrefWidth(280);

        Label lblPassword = new Label("Password");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Buat password");

        Label lblKonfirmasi = new Label("Konfirmasi Password");
        PasswordField pfKonfirmasi = new PasswordField();
        pfKonfirmasi.setPromptText("Ulangi password");

        Label lblRole = new Label("Role");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("admin", "petugas");
        cbRole.setValue("petugas");
        cbRole.setPrefWidth(280);

        // ── TOMBOL ──
        Button btnDaftar = new Button("DAFTAR");
        btnDaftar.setPrefWidth(280);
        btnDaftar.setStyle("-fx-background-color: #b71c1c; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10;");

        Button btnKembali = new Button("Sudah punya akun? Login");
        btnKembali.setStyle("-fx-background-color: transparent; " +
                "-fx-text-fill: #b71c1c; -fx-border-color: transparent;");

        // ── AKSI DAFTAR ──
        btnDaftar.setOnAction(e -> {
            String username = tfUsername.getText().trim();
            String password = pfPassword.getText().trim();
            String konfirmasi = pfKonfirmasi.getText().trim();
            String role = cbRole.getValue();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Semua field wajib diisi!");
                return;
            }
            if (!password.equals(konfirmasi)) {
                showAlert("Password dan konfirmasi tidak cocok!");
                return;
            }
            if (password.length() < 6) {
                showAlert("Password minimal 6 karakter!");
                return;
            }

            try {
                if (userDAO.usernameAda(username)) {
                    showAlert("Username sudah dipakai, pilih yang lain!");
                    return;
                }
                if (userDAO.registrasi(username, password, role)) {
                    showAlert("Registrasi berhasil! Silahkan login.");
                    stage.close();
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        btnKembali.setOnAction(e -> stage.close());

        // ── LAYOUT ──
        VBox form = new VBox(10,
                lblUsername, tfUsername,
                lblPassword, pfPassword,
                lblKonfirmasi, pfKonfirmasi,
                lblRole, cbRole,
                btnDaftar, btnKembali);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER_LEFT);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        VBox root = new VBox(20, lblJudul, form);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Scene scene = new Scene(root, 380, 500);
        stage.setTitle("Registrasi - Koperasi Merah Putih");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String pesan) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}