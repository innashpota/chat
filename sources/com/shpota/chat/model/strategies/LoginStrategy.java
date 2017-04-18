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
        User user = chatRepository.loginUser(login, password);

        if (user != null) {
            List<User> users = chatRepository.getAllUsers();
            int userId = user.getId();

            return new AllUsersServerPackage(userId, users);
        }
        return new ErrorServerPackage(
                "The login or password you entered is incorrect"
        );
    }
}
