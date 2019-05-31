package controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.chat.Chat;
import models.message.Message;
import models.message.TextMessage;
import client.Main;

public class ChatController {
    public static ChatController instance;
    { instance = this; }

    public ListView chats;
    public ListView listView;

    public void updateChats() {
        chats.getItems().clear();
        Main.user.getChats().forEach(chat -> chats.getItems().add(chat));
    }

    public void initialize() {
        updateChats();
        chats.setCellFactory(param -> new ListCell<Chat>() {
            @Override
            public void updateItem(Chat chat, boolean empty) {
                super.updateItem(chat, empty);
                if(!empty) {
                    setText(chat.getTitle());
                    setGraphicTextGap(10);
                    Label label = new Label(("" + chat.getTitle().charAt(0)).toUpperCase());
                    label.setStyle("-fx-background-color: " + chat.getColor() + ";");
                    label.getStyleClass().add("chatIcon");
                    setGraphic(label);
                }
            }
        });

        listView.setRotate(180);
        listView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            public void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                setRotate(180);
                if(empty) return;
                if(message instanceof TextMessage) {
                    Label messageLabel = new Label(((TextMessage) message).getText());
                    messageLabel.getStyleClass().add("messageLabel");
                    if(messageLabel.getText().length() > 30) {
                        messageLabel.setMaxWidth(400);
                        messageLabel.setWrapText(true);
                        messageLabel.setText(messageLabel.getText() + "                                                                          ");
                    }
                    setGraphic(messageLabel);
                    if(message.getUser().equals(Main.user)) {
                        messageLabel.getStyleClass().add("messageRight");
                        setAlignment(Pos.CENTER_RIGHT);
                    } else {
                        messageLabel.getStyleClass().add("messageLeft");
                        setAlignment(Pos.CENTER_LEFT);
                    }
                }
            }
        });

    }

    public void sendMessage(ActionEvent actionEvent) {

    }
}
