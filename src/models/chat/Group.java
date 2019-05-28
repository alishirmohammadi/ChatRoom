package models.chat;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<User> users = new ArrayList<>();
    private User admin;
}
