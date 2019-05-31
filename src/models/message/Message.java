package models.message;

import com.gilecode.yagson.YaGson;
import models.User;

public abstract class Message {
    private int id;
    private User user;
    private int replyMessageId;
    private int chatId;

    public User getUser() {
        return user;
    }

    public int getReplyMessage() {
        return replyMessageId;
    }

    public Message(User user, int chatId, int replyMessageId) {
        this.user = user;
        this.chatId = chatId;
        this.replyMessageId = replyMessageId;
    }

    @Override public String toString() {
        return new YaGson().toJson(this);
    }

    public int getChatId() {
        return chatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
