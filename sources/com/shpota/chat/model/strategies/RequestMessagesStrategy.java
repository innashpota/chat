package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.ErrorServerPackage;
import com.shpota.chat.model.packages.MessageServerPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.RequestMessagesClientPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestMessagesStrategy
        implements Strategy<RequestMessagesClientPackage> {
    private final ChatRepository chatRepository;

    public RequestMessagesStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(RequestMessagesClientPackage pkg) {
        int authorId = pkg.getAuthorId();
        int destinationId = pkg.getDestinationId();
        User destination = chatRepository.getUser(destinationId);

        if (destination != null) {
            Map<Integer, String> userMap = getUserNames(authorId, destinationId);
            List<Message> messages = chatRepository.getMessages(
                    authorId,
                    destinationId
            );

            return new MessageServerPackage(messages, userMap);
        }
        return new ErrorServerPackage(
                "You did not choose the recipient of the message"
        );
    }

    private Map<Integer, String> getUserNames(int authorId, int destinationId) {
        User destination = chatRepository.getUser(destinationId);
        User author = chatRepository.getUser(authorId);
        Map<Integer, String> userMap = new HashMap<>();
        userMap.put(
                authorId,
                author.getFirstName() + " " + author.getLastName()
        );
        userMap.put(
                destinationId,
                destination.getFirstName() + " " + destination.getLastName()
        );
        return userMap;
    }
}
