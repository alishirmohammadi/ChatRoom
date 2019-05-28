package models;

import models.chat.Chat;

import java.util.List;

public class User {
    private int id;
    private String username;
    private List<Chat> chats;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
