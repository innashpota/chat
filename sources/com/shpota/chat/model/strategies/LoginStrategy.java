package com.shpota.chat.model.strategies;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.User;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.LoginClientPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.packages.ErrorServerPackage;

import java.util.List;

public class LoginStrategy implements Strategy<LoginClientPackage> {
    private final ChatRepository chatRepository;

    public LoginStrategy(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Package handle(LoginClientPackage pkg) {
        String login = pkg.getLogin();
        String password = pkg.getPassword();

        if (chatRepository.loginUser(login, password) != null) {
            List<User> users = chatRepository.getAllUsers();

            return new AllUsersServerPackage(users);
        }
        return new ErrorServerPackage(
                "The login or password you entered is incorrect"
        );
    }
}
