package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.packages.AddMessageClientPackage;
import com.shpota.chat.model.packages.MessageServerPackage;
import com.shpota.chat.model.packages.Package;

public class MessageStrategy implements Strategy<AddMessageClientPackage> {
    private final ChatRepository chatRepository;

    public MessageStrategy(ChatRepository chatRepository) {
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
        return new MessageServerPackage(chatRepository.getMessages(
                pkg.getAuthorId(),
                pkg.getDestinationId())
        );
    }
}
