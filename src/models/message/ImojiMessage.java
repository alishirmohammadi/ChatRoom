package models.message;

import com.gilecode.yagson.YaGson;
import models.User;

public class ImojiMessage extends Message {
    public ImojiMessage(User user, int chatId, int replyMessageId) {
        super(user, chatId, replyMessageId);
    }

    @Override public String toString() {
        return new YaGson().toJson(this);
    }

}
