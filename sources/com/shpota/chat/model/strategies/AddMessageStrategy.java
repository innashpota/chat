package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.AddMessageClientPackage;
import com.shpota.chat.model.packages.MessageServerPackage;
import com.shpota.chat.model.packages.Package;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMessageStrategy implements Strategy<AddMessageClientPackage> {
    private final ChatRepository chatRepository;

    public AddMessageStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(AddMessageClientPackage pkg) {
        chatRepository.addMessage(new Message(
                pkg.getAuthorId(),
                pkg.getDestinationId(),
                pkg.getPostedDate(),
                pkg.getMessage()
        ));
        Map<Integer, String> userMap = getUserNames(pkg);
        List<Message> messages = chatRepository.getMessages(
                pkg.getAuthorId(),
                pkg.getDestinationId()
        );
        return new MessageServerPackage(messages, userMap);
    }

    private Map<Integer, String> getUserNames(AddMessageClientPackage pkg) {
        User destination = chatRepository.getUser(pkg.getDestinationId());
        User author = chatRepository.getUser(pkg.getAuthorId());
        Map<Integer, String> userMap = new HashMap<>();
        userMap.put(
                pkg.getAuthorId(),
                author.getFirstName() + " " + author.getLastName()
        );
        userMap.put(
                pkg.getDestinationId(),
                destination.getFirstName() + " " + destination.getLastName()
        );
        return userMap;
    }
}
