package com.shpota.chat.model;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName, lastName, login, password;
    private int id;

    public User(int id, String firstName, String lastName, String login, String password) {
        this(firstName, lastName, login, password);
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;

        if (id != user.id)
            return false;
        if (!firstName.equals(user.firstName))
            return false;
        if (!lastName.equals(user.lastName))
            return false;
        if (!login.equals(user.login))
            return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", login = '" + login + '\'' +
                ", password = '" + password + '\'' +
                '}';
    }
}
