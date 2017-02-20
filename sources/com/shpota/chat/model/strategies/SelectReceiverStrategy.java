package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.ClientSelectReceiverPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ServerErrorPackage;
import com.shpota.chat.model.packages.ServerMessagePackage;

public class SelectReceiverStrategy
        implements Strategy<ClientSelectReceiverPackage> {
    private final ChatRepository chatRepository;

    public SelectReceiverStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(ClientSelectReceiverPackage pkg) {
        User receiver = getReceiverMessage(pkg);
        if (receiver != null) {
            return new ServerMessagePackage(
                    chatRepository.getMessages(pkg.getAuthorId(), receiver.getId())
            );
        }
        return new ServerErrorPackage(
                "You did not choose the recipient of the message"
        );
    }

    private User getReceiverMessage(ClientSelectReceiverPackage pkg) {
        return chatRepository.getUserByLogin(pkg.getLogin());
    }
}
