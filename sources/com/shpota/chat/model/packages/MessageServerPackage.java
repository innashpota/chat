package com.shpota.chat.model.packages;

import com.shpota.chat.model.Message;

import java.util.List;

public class MessageServerPackage extends Package {
    private final List<Message> messages;

    public MessageServerPackage(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "MessageServerPackage{" +
                "messages=" + messages +
                '}';
    }
}
