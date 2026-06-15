package view;

import dao.UserDAO;
import model.User;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView extends Application {

    private UserDAO userDAO = new UserDAO();

    @Override
    public void start(Stage stage) {

        // ── JUDUL ──
        Label lblJudul = new Label("Koperasi Merah Putih");
        lblJudul.setFont(Font.font("Arial", 22));
        lblJudul.setStyle("-fx-font-weight: bold; -fx-text-fill: #b71c1c;");

        Label lblSub = new Label("Sistem Informasi Koperasi");
        lblSub.setStyle("-fx-text-fill: #555;");

        VBox judul = new VBox(5, lblJudul, lblSub);
        judul.setAlignment(Pos.CENTER);

        // ── FORM LOGIN ──
        Label lblUsername = new Label("Username");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Masukkan username");
        tfUsername.setPrefWidth(280);

        Label lblPassword = new Label("Password");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Masukkan password");
        pfPassword.setPrefWidth(280);

        // ── TOMBOL ──
        Button btnLogin = new Button("LOGIN");
        btnLogin.setPrefWidth(280);
        btnLogin.setStyle("-fx-background-color: #b71c1c; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10;");

        Button btnKeRegister = new Button("Belum punya akun? Daftar di sini");
        btnKeRegister.setStyle("-fx-background-color: transparent; -fx-text-fill: #b71c1c; " +
                "-fx-border-color: transparent;");

        // ── AKSI LOGIN ──
        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText().trim();
            String password = pfPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Username dan password wajib diisi!");
                return;
            }

            try {
                User user = userDAO.login(username, password);
                if (user != null) {
                    showAlert("Selamat datang, " + user.getUsername() + "! (" + user.getRole() + ")");
                    // Nanti di sini pindah ke DashboardView
                    stage.close();
                    new DashboardView(user.getUsername(), user.getRole()).start(new Stage());
                } else {
                    showAlert("Username atau password salah!");
                }
            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        // BARU
        btnKeRegister.setOnAction(e -> {
            new RegisterView().start(new Stage());
        });

        // ── LAYOUT ──
        VBox form = new VBox(10,
                lblUsername, tfUsername,
                lblPassword, pfPassword,
                btnLogin, btnKeRegister);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER_LEFT);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        form.setMaxWidth(340);

        VBox root = new VBox(20, judul, form);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Scene scene = new Scene(root, 420, 450);
        stage.setTitle("Login - Koperasi Merah Putih");
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

    public static void main(String[] args) {
        launch(args);
    }
}