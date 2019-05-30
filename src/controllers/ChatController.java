package controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.User;
import models.message.Message;
import models.message.TextMessage;
import client.Main;

public class ChatController {
    public static ChatController instance;
    { instance = this; }

    public ListView chats;
    public ListView listView;

    public void initialize() {
        User user1 = new User("Mohammad", 2);
        User user2 = new User("Ahmad", 3);
        User user3 = new User("Hamid", 4);
        User user4 = new User("Reza", 5);

        chats.setCellFactory(param -> new ListCell<User>() {

            @Override
            public void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if(!empty) {
                    setText(user.getName());
                    setGraphicTextGap(10);
                    Label label = new Label(("" + user.getName().charAt(0)).toUpperCase());
                    label.setStyle("-fx-background-color: coral;");
                    label.getStyleClass().add("chatIcon");
                    setGraphic(label);
                }
            }
        });
        chats.getItems().addAll(user1, user2, user3, user4);
        Message message0 = new TextMessage(1, Main.user, null, "با توجه به مشکلات پیش آمده برای اینترنت دانشگاه، پیشنهاد می\u200Cشود حداکثر همکاری را با یکدیگر در خصوص انتقال منابع داشته باشید و در صورت تمایل و آمادگی دستیاران آموزشی از آن\u200Cها در این امر کمک بگیرید.");
        Message message1 = new TextMessage(1, Main.user, null, "سلام.");
        Message message2 = new TextMessage(1, Main.user, null, "خوبی؟");
        Message message3 = new TextMessage(1, user1, null, "سلام!");
        Message message4 = new TextMessage(1, user1, null, "ممنون تو خوبی؟");
        Message message5 = new TextMessage(1, user1, null, "با توجه به مشکلات پیش آمده برای اینترنت دانشگاه، پیشنهاد می\u200Cشود حداکثر همکاری را با یکدیگر در خصوص انتقال منابع داشته باشید و در صورت تمایل و آمادگی دستیاران آموزشی از آن\u200Cها در این امر کمک بگیرید.");
        listView.getItems().add(0, message0);
        listView.getItems().add(0, message1);
        listView.getItems().add(0, message2);
        listView.getItems().add(0, message3);
        listView.getItems().add(0, message4);
        listView.getItems().add(0, message5);

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
