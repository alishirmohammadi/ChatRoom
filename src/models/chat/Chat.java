package models.chat;

import models.message.Message;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<>();
    private boolean isPrivateChat;

    public String getTitle() {
        return null;
    }

    protected Chat(boolean isPrivateChat) {
        this.isPrivateChat = isPrivateChat;
    }

    public boolean isPrivateChat() {
        return isPrivateChat;
    }
}
