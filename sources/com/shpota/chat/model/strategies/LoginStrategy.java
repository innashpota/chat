package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.packages.ClientLoginPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ServerAllUsersPackage;
import com.shpota.chat.model.packages.ServerErrorPackage;

public class LoginStrategy implements Strategy<ClientLoginPackage>{
    private final ChatRepository chatRepository;

    public LoginStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(ClientLoginPackage pkg) {
        if (chatRepository.loginUser(pkg.getLogin(), pkg.getPassword()) != null) {
            return new ServerAllUsersPackage(chatRepository.getAllUsers());
        }
        return new ServerErrorPackage(
                "User with that login and /or password does not exist"
        );
    }
}
