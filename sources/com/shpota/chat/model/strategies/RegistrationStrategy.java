package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.RegistrationClientPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ErrorServerPackage;

import java.util.List;

public class RegistrationStrategy implements Strategy<RegistrationClientPackage> {
    private final ChatRepository chatRepository;

    public RegistrationStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(RegistrationClientPackage pkg) {
        String login = pkg.getLogin();
        User sender = chatRepository.getUserByLogin(login);

        if (sender == null) {
            chatRepository.addUser(new User(
                    pkg.getFirstName(),
                    pkg.getLastName(),
                    pkg.getLogin(),
                    pkg.getPassword()
            ));
            List<User> users = chatRepository.getAllUsers();

            return new AllUsersServerPackage(users);
        }
        return new ErrorServerPackage("User with this login already exists");
    }
}
