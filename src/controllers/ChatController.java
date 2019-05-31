package controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.chat.Chat;
import models.message.Message;
import models.message.TextMessage;
import client.Main;

import java.util.Iterator;

public class ChatController {
    public static ChatController instance;
    public TextField messageInput;
    public ListView chats;
    public ListView listView;

    { instance = this; }

    public void updateChats() {
        chats.getItems().clear();
        Main.user.getChats().forEach(chat -> chats.getItems().add(chat));
    }

    public void updateMessages() {
        listView.getItems().removeAll(listView.getItems());
        listView.getSelectionModel().clearSelection();

        Chat selectedChat = (Chat) chats.getSelectionModel().getSelectedItem();
        if(selectedChat == null) return;
        listView.getItems().addAll(selectedChat.getMessages());
        System.out.println(selectedChat.getMessages().size() + " " + listView.getItems().size());
    }


    public void initialize() {
        messageInput.setDisable(true);

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
                } else {
                    setText("");
                    setGraphic(new Label(""));
                }
            }
        });

        listView.setRotate(180);
        listView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            public void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                setRotate(180);
                if(empty) {
                    setText("");
                    setGraphic(new Label(""));
                    return;
                }
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
        String text = messageInput.getText().trim();
        if(text.equals(""))
            return;
        Chat selectedChat = (Chat) chats.getSelectionModel().getSelectedItem();
        int chatId = selectedChat.getId();
        messageInput.setText("");
        Message message = new TextMessage(Main.user, chatId, -1, text);
        if(Main.connection.sendMessage(message)) {
            selectedChat.getMessages().add(0, message);
            updateMessages();
        }
    }

    public void chatChange(MouseEvent mouseEvent) {
        messageInput.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        updateMessages();
    }
}
