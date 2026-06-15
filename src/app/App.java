package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView();
        loginView.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}