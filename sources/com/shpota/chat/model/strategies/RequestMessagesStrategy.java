package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.RequestMessagesClientPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ErrorServerPackage;
import com.shpota.chat.model.packages.MessageServerPackage;

import java.util.List;

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
            List<Message> messages = chatRepository.getMessages(
                    authorId,
                    destinationId
            );

            return new MessageServerPackage(messages);
        }
        return new ErrorServerPackage(
                "You did not choose the recipient of the message"
        );
    }
}
