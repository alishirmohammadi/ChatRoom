package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../views/chat.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 800, 600));
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
        // File file = new FileChooser().showOpenDialog(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
