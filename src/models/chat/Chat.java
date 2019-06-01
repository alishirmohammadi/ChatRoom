package models.chat;

import com.gilecode.yagson.YaGson;
import models.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<>();
    private int id;
    private boolean isPrivateChat;

    public String getTitle() {
        return null;
    }

    public String getColor() {
        return "";
    }

    protected Chat(boolean isPrivateChat) {
        this.isPrivateChat = isPrivateChat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPrivateChat() {
        return isPrivateChat;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return new YaGson().toJson(this);
    }
}
