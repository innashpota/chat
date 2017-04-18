package com.shpota.chat.model.packages;

import com.shpota.chat.model.Message;

import java.util.List;
import java.util.Map;

public class MessageServerPackage extends Package {
    private final List<Message> messages;
    private final Map<Integer, String> userMap;

    public MessageServerPackage(List<Message> messages, Map<Integer, String> userMap) {
        this.messages = messages;
        this.userMap = userMap;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Map<Integer, String> getUserMap() {
        return userMap;
    }
}
