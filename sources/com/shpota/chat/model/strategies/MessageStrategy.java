package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.packages.ClientAddMessagePackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ServerMessagePackage;

public class MessageStrategy implements Strategy<ClientAddMessagePackage> {
    private final ChatRepository chatRepository;

    public MessageStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(ClientAddMessagePackage pkg) {
        chatRepository.addMessage(new Message(
                pkg.getAuthorID(),
                pkg.getDestinationID(),
                pkg.getPostedDate(),
                pkg.getMessage()
        ));
        return new ServerMessagePackage(
                chatRepository.getMessages(
                        pkg.getAuthorId(),
                        pkg.getDestinationID())
        );
    }
}
