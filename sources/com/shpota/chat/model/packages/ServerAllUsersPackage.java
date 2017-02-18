package com.shpota.chat.model.packages;

import com.shpota.chat.model.User;

import java.util.List;

public class ServerAllUsersPackage extends Package {
    private final List<User> allUsers;


    public ServerAllUsersPackage(List<User> allUsers) {
        this.allUsers = allUsers;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    @Override
    public String toString() {
        return "ServerAllUsersPackage{" +
                "allUsers=" + allUsers +
                '}';
    }
}
