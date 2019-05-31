package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
    private static Stage stage;
    public static ServerConnection connection;
    public static User user = new User("Ali", 1);

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        user = createUser();
        if(user.getName().equals("")) return;
        connection = new ServerConnection();
        connection.connectServer();
        connection.sendUser(user);
        connection.start();
        showChat();
    }

    public void showChat() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("/views/chat.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public User createUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome");
        dialog.setHeaderText("Welcome");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        return new User(result.orElse(""), 0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
