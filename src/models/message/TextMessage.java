package models.message;

import com.gilecode.yagson.YaGson;
import models.User;

public class TextMessage extends Message {
    private String text;

    public TextMessage(User user, int chatId, int replyMessageId, String text) {
        super(user, chatId, replyMessageId);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override public String toString() {
        return new YaGson().toJson(this);
    }

}
