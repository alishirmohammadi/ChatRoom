package models.chat;

import models.User;

public class PrivateChat extends Chat {
    private User user;

    public PrivateChat(User user) {
        super(true);
        this.user = user;
    }

    @Override
    public String getColor() {
        return user.getColor();
    }

    @Override
    public String getTitle() {
        return user.getName();
    }
}
