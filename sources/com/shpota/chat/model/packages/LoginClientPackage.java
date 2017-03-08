package com.shpota.chat.model.packages;

public class LoginClientPackage extends Package {
    private final String login;
    private final String password;

    public LoginClientPackage(String login, String password) {
        if (login == null || password == null) {
            throw new IllegalArgumentException(
                    "Login and password must not be null."
            );
        }
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
