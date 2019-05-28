package models.message;

import com.google.gson.Gson;
import models.User;

public class ImojiMessage extends Message {
    public ImojiMessage(int id, User user, Message replyMessage) {
        super(id, user, replyMessage);
    }

    @Override public String toString() {
        return new Gson().toJson(this);
    }

}
