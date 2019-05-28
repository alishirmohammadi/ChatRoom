package models.message;

import com.google.gson.Gson;
import models.User;

public abstract class Message {
    private int id;
    private User user;
    private Message replyMessage;

    public User getUser() {
        return user;
    }

    public Message getReplyMessage() {
        return replyMessage;
    }

    public Message(int id, User user, Message replyMessage) {
        this.id = id;
        this.user = user;
        this.replyMessage = replyMessage;
    }

    @Override public String toString() {
        return new Gson().toJson(this);
    }

}
