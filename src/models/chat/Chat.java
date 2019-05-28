package models.chat;

import models.message.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Chat {
    private List<Message> messages = new ArrayList<>();
}
