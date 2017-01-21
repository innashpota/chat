package com.shpota.chat.model;

public class User {
    private String firstName, lastName, login, password;
    private int id;

    public User(int id, String firstName, String lastName, String login, String password) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public User(String firstName, String lastName, String login, String password) {
        if (firstName == null || lastName == null || login == null || password == null) {
            throw new IllegalArgumentException(
                    "First name, last name, login and password must not be null."
            );
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name must not be null.");
        }
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name must not be null.");
        }
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login must not be null.");
        }
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password must not be null.");
        }
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
