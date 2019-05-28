package models;

import com.google.gson.Gson;
import models.chat.Chat;

import java.util.List;

public class User {
    private int id;
    private String username;
    private List<Chat> chats;

    public User(String username, int id) {
        this.username = username;
        this.id = id;
    }

    @Override public boolean equals(Object object) {
        if(object == null) return false;
        return object instanceof User && ((User) object).id == id;
    }

    @Override public String toString() {
        return new Gson().toJson(this);
    }

    public String getName() {
        return username;
    }
}
