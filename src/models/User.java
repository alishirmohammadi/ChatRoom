package models;

import com.gilecode.yagson.YaGson;
import models.chat.Chat;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private List<Chat> chats = new ArrayList<>();

    public User(String username, int id) {
        this.username = username;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username);
    }

    @Override public String toString() {
        return new YaGson().toJson(this);
    }

    public String getName() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Chat> getChats() {
        return chats;
    }
}
