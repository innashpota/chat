package com.shpota.chat.model.packages;

import com.shpota.chat.model.User;

import java.util.List;

public class AllUsersServerPackage extends Package {
    private final List<User> allUsers;
    private final int userId;

    public AllUsersServerPackage(int userId, List<User> allUsers) {
        this.allUsers = allUsers;
        this.userId = userId;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public int getUserId() {
        return userId;
    }
}
