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
        if (chatRepository.getUserByLogin(pkg.getLogin()) == null) {
            chatRepository.addUser(new User(
                    pkg.getFirstName(),
                    pkg.getLastName(),
                    pkg.getLogin(),
                    pkg.getPassword()
            ));
            return new ServerAllUsersPackage(chatRepository.getAllUsers());
        }
        return new ServerErrorPackage("User with this login already exists");
    }
}
