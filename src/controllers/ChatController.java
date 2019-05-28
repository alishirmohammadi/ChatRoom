package controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;

public class ChatController {
    public static ChatController instance;
    { instance = this; }

    public ListView chats;
    public ListView messages;

    public void initialize() {
        chats.getItems().addAll("Ali", "Mohammad", "Hamid", "مامان");
        chats.setCellFactory(param -> new ListCell<String>() {

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if(!empty) {
                    setText(name);
                    setGraphicTextGap(10);
                    Label label = new Label(("" + name.charAt(0)).toUpperCase());
                    label.setStyle("-fx-background-color: coral;");
                    label.getStyleClass().add("chatIcon");
                    setGraphic(label);
                }
            }
        });
    }

    public void sendMessage(ActionEvent actionEvent) {

    }
}
