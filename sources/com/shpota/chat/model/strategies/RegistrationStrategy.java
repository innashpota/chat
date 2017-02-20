package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.ClientRegistrationPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ServerAllUsersPackage;
import com.shpota.chat.model.packages.ServerErrorPackage;

public class RegistrationStrategy implements Strategy<ClientRegistrationPackage> {
    private final ChatRepository chatRepository;

    public RegistrationStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(ClientRegistrationPackage pkg) {
        User sender = getSenderMessage(pkg);
        if (sender == null) {
            chatRepository.addUser(new User(
                    pkg.getFirstName(),
                    pkg.getLastName(),
                    pkg.getLogin(),
                    pkg.getPassword()
            ));
            ServerAllUsersPackage allUsersPackage =
                    new ServerAllUsersPackage(chatRepository.getAllUsers());
            allUsersPackage.setAuthorId(sender.getId());
            return allUsersPackage;
        }
        return new ServerErrorPackage("User with this login already exists");
    }

    private User getSenderMessage(ClientRegistrationPackage pkg) {
        return chatRepository.getUserByLogin(pkg.getLogin());
    }
}
