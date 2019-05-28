package models.message;

import com.google.gson.Gson;
import models.User;

public class TextMessage extends Message {
    private String text;

    public TextMessage(int id, User user, Message replyMessage, String text) {
        super(id, user, replyMessage);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override public String toString() {
        return new Gson().toJson(this);
    }

}
