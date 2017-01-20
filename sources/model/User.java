package model;

public class User {
    private String firstName, lastName, login, password;

    public User(String firstName, String lastName, String login, String password) {
        if (firstName == null || lastName == null || login == null || password == null) {
            throw new IllegalArgumentException(
                    "First name, last name, login and password can not be null."
            );
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name can not be null.");
        }
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name can not be null.");
        }
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login can not be null.");
        }
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password can not be null.");
        }
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
