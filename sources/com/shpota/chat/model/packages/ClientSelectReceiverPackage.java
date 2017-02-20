package com.shpota.chat.model.packages;

public class ClientSelectReceiverPackage extends Package{
    private final String login;

    public ClientSelectReceiverPackage(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
