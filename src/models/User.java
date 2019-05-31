package models;

import com.gilecode.yagson.YaGson;
import models.chat.Chat;

import java.util.*;

public class User {
    private int id;
    private String username;
    private Set<Chat> chats = new HashSet<>();
    private String color;
    private static final String[] COLORS = new String[] {"coral", "darkcyan", "green", "darkgreen", "crimson", "Salmon", "DarkSalmon", "LightCoral", "IndianRed", "Tomato", "DarkKhaki", "BlueViolet", "DarkViolet", "DarkMagenta", "DarkSlateBlue", "Indigo", "CadetBlue", "DarkOliveGreen", "SaddleBrown", "RosyBrown", "MediumSeaGreen"};

    public User(String username, int id) {
        this.username = username;
        this.id = id;
        this.color = COLORS[new Random().nextInt(COLORS.length)];
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

    public Set<Chat> getChats() {
        return chats;
    }

    public String getColor() {
        return color;
    }
}
