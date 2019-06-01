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
import java.util.Optional;

public class ChatController {
    public static ChatController instance;
    public TextField messageInput;
    public ListView chats;
    public ListView listView;
    public Button imageButton;
    public Button emoji1;
    public Button emoji2;
    public Button emoji3;
    public Button emoji4;
    public Button emoji5;
    public Button emoji6;
    public Button emoji7;
    public Button emoji8;
    public Button emoji9;
    public Button emoji10;
    public Button emoji11;
    public Label name;
    public Button createGroup;

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
        name.setText(Main.user.getName());
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
        emoji1.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji2.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji3.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji4.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji5.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji6.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji7.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji8.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji9.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji10.setDisable(chats.getSelectionModel().getSelectedItem() == null);
        emoji11.setDisable(chats.getSelectionModel().getSelectedItem() == null);
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

    public void emoji1(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji1.getText()); }
    public void emoji2(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji2.getText()); }
    public void emoji3(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji3.getText()); }
    public void emoji4(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji4.getText()); }
    public void emoji5(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji5.getText()); }
    public void emoji6(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji6.getText()); }
    public void emoji7(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji7.getText()); }
    public void emoji8(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji8.getText()); }
    public void emoji9(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji9.getText()); }
    public void emoji10(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji10.getText()); }
    public void emoji11(MouseEvent mouseEvent) { messageInput.setText(messageInput.getText() + emoji11.getText()); }

    public void createGroup(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create group");
        dialog.setHeaderText("Create group");
        dialog.setContentText("Enter members name (split with ,):");
        String members = dialog.showAndWait().orElse("").trim();
        if(members.equals(""))
            return;
        TextInputDialog nameDialog = new TextInputDialog();
        dialog.setTitle("Create group");
        dialog.setHeaderText("Create group");
        dialog.setContentText("Enter group name:");
        String name = dialog.showAndWait().orElse("").trim();
        if(name.equals(""))
            return;
        Main.connection.createGroup(name, members);
    }
}
