package controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import models.chat.Chat;
import models.chat.PrivateChat;
import models.message.ImageMessage;
import models.message.Message;
import models.message.TextMessage;
import client.Main;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;

public class ChatController {
    public static ChatController instance;
    public TextField messageInput;
    public ListView chats;
    public ListView listView;
    public Button imageButton;

    { instance = this; }

    public void updateChats() {
        chats.getItems().clear();
        Main.user.getChats().forEach(chat -> chats.getItems().add(chat));
    }

    public void updateMessages() {
        listView.getItems().clear();
        listView.getSelectionModel().clearSelection();

        Chat selectedChat = (Chat) chats.getSelectionModel().getSelectedItem();
        if(selectedChat == null) return;
        selectedChat.getMessages().forEach(message -> listView.getItems().add(message));
    }


    public void initialize() {
        messageInput.setDisable(true);

        updateChats();
        chats.setCellFactory(param -> new ListCell<Chat>() {
            @Override
            public void updateItem(Chat chat, boolean empty) {
                super.updateItem(chat, empty);
                if(!empty) {
                    boolean hasProgileImage = chat instanceof PrivateChat && !((PrivateChat) chat).getUser().getProfileImage().equals("");
                    setText(chat.getTitle());
                    setGraphicTextGap(10);
                    Label label = new Label("");
                    if(!hasProgileImage) {
                        label.setStyle("-fx-background-color: " + chat.getColor() + ";");
                        label.setText(("" + chat.getTitle().charAt(0)).toUpperCase());
                    } else
                        label.setStyle("-fx-background-image: url(http://localhost:8231/" + ((PrivateChat) chat).getUser().getProfileImage() + ");" +
                                "-fx-background-size: 50px 50px; " +
                                "-fx-background-radius: 25; ");
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
                if (!empty) {
                    if (message instanceof TextMessage) {
                        Label messageLabel = new Label(((TextMessage) message).getText());
                        messageLabel.getStyleClass().add("messageLabel");
                        if (messageLabel.getText().length() > 30) {
                            messageLabel.setMaxWidth(400);
                            messageLabel.setWrapText(true);
                            messageLabel.setText(messageLabel.getText() + "                                                                                                                                                                            ");
                        }
                        if (message.getUser().equals(Main.user)) {
                            messageLabel.getStyleClass().add("messageRight");
                            setAlignment(Pos.CENTER_RIGHT);
                        } else {
                            messageLabel.getStyleClass().add("messageLeft");
                            setAlignment(Pos.CENTER_LEFT);
                        }
                        setGraphic(messageLabel);
                    } else if(message instanceof ImageMessage) {
                        ImageMessage imageMessage = (ImageMessage) message;
                        Image image = new Image("http://localhost:8231/" + imageMessage.getFileName());
                        ImageView imageView = new ImageView();
                        imageView.setImage(image);
                        imageView.maxWidth(400);
                        if(imageMessage.getUser().equals(Main.user)) {
                            setAlignment(Pos.CENTER_RIGHT);
                        } else {
                            setAlignment(Pos.CENTER_LEFT);
                        }
                        setText("");
                        setGraphic(imageView);

                    }
                } else {
                    setText("");
                    setGraphic(new Label(""));
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
            listView.getItems().add(0, message);
        }
    }

    public void showMessage(Message message, Chat chat) {
        Chat selectedChat = (Chat) chats.getSelectionModel().getSelectedItem();
        if(chat == selectedChat)
            listView.getItems().add(0, message);
    }

    public void chatChange(MouseEvent mouseEvent) {
        messageInput.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        imageButton.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        updateMessages();
    }

    public void sendImage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image to send.");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Jpeg Image", "*.jpg")
        );
        File image = fileChooser.showOpenDialog(Main.stage);
        if(image == null)
            return;
        Chat selectedChat = (Chat) chats.getSelectionModel().getSelectedItem();
        if(selectedChat == null)
            return;
        try {
            Main.connection.sendImage(image, selectedChat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
