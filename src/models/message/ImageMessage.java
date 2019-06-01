package models.message;

import com.gilecode.yagson.YaGson;
import models.User;

public class ImageMessage extends Message {
    private String fileName;

    public ImageMessage(User user, int chatId, int replyMessageId, String fileName) {
        super(user, chatId, replyMessageId);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override public String toString() {
        return new YaGson().toJson(this);
    }
}