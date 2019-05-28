package models.message;

import models.User;

public abstract class Message {
    private int id;
    private User user;
    private Message replyMessage;
}
