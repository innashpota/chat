package com.shpota.chat.model.packages;

public class ClientLoginPackage extends Package {
    private final String login;
    private final String password;

    public ClientLoginPackage(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
