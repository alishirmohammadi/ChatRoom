package models.chat;

import models.User;
import java.util.List;

public class Group extends Chat {
    private List<User> users;
    private User admin;
    private String title;

    public Group(String title, User admin, List<User> users) {
        super(false);
        this.users = users;
        this.admin = admin;
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
