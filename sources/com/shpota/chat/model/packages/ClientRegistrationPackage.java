package com.shpota.chat.model.packages;

public class ClientRegistrationPackage extends Package {
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String password;

    public ClientRegistrationPackage(
            String firstName,
            String lastName,
            String login,
            String password
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
