package com.shpota.chat.model.packages;

import com.shpota.chat.model.User;

import java.util.List;

public class AllUsersServerPackage extends Package {
    private final List<User> allUsers;

    public AllUsersServerPackage(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    @Override
    public String toString() {
        return "AllUsersServerPackage{" +
                "allUsers=" + allUsers +
                '}';
    }
}
