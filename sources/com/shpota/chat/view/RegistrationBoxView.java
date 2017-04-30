package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.Package;
import org.apache.log4j.Logger;

import javax.swing.Box;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.Box.*;

public class RegistrationBoxView extends View {
    private final static Logger LOGGER = Logger.getLogger(LoginWindowView.class);
    public final Box registrationBox;
    private final JFrame loginViewFrame;
    private final JTextField firstNameField = new JTextField(20);
    private final JTextField lastNameField = new JTextField(20);
    private final JTextField loginField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JPasswordField repeatPasswordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();

    public RegistrationBoxView(ClientModel model, JFrame loginViewFrame) {
        super(model);
        this.loginViewFrame = loginViewFrame;
        this.registrationBox = createRegistrationBox();
    }

    @Override
    public void show() {
        registrationBox.setVisible(true);
    }

    @Override
    public void hide() {
        registrationBox.setVisible(false);
    }

    @Override
    public void onPackageReceived(Package pkg) {
        if (pkg instanceof AllUsersServerPackage) {
            loginViewFrame.setVisible(false);
        }
    }

    private Box createRegistrationBox() {
        Box mainBox = createVerticalBox();
        mainBox.setBorder(new EmptyBorder(50, 200, 300, 200));
        mainBox.add(createFirstNameBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createLastNameBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createLoginBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createPasswordBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createRepeatPasswordBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(createRegistrationButtonBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(errorMessageBox());
        mainBox.add(createVerticalStrut(30));
        return mainBox;
    }

    private Box createFirstNameBox() {
        Box firstNameBox = createHorizontalBox();
        JLabel fistNameLabel = new JLabel("First name*");
        firstNameBox.add(fistNameLabel);
        firstNameBox.add(createHorizontalStrut(46));
        firstNameBox.add(firstNameField);
        return firstNameBox;
    }

    private Box createLastNameBox() {
        Box lastNameBox = createHorizontalBox();
        JLabel lastNameLabel = new JLabel("Last name*");
        lastNameBox.add(lastNameLabel);
        lastNameBox.add(createHorizontalStrut(46));
        lastNameBox.add(lastNameField);
        return lastNameBox;
    }

    private Box createLoginBox() {
        Box loginBox = createHorizontalBox();
        JLabel loginLabel = new JLabel("Login*");
        loginBox.add(loginLabel);
        loginBox.add(createHorizontalStrut(75));
        loginBox.add(loginField);
        return loginBox;
    }

    private Box createPasswordBox() {
        Box passwordBox = createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password*");
        passwordBox.add(passwordLabel);
        passwordBox.add(createHorizontalStrut(48));
        passwordBox.add(passwordField);
        return passwordBox;
    }

    private Box createRepeatPasswordBox() {
        Box repeatPasswordBox = createHorizontalBox();
        JLabel repeatPasswordLabel = new JLabel("Repeat password*");
        repeatPasswordBox.add(repeatPasswordLabel);
        repeatPasswordBox.add(createHorizontalStrut(6));
        repeatPasswordBox.add(repeatPasswordField);
        return repeatPasswordBox;
    }

    private Box createRegistrationButtonBox() {
        Box buttonBox = createHorizontalBox();
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new RegistrationActionListener());
        buttonBox.add(createHorizontalGlue());
        buttonBox.add(submitButton);
        return buttonBox;
    }

    private Box errorMessageBox() {
        Box errorMessageBox = createHorizontalBox();
        errorMessageBox.add(errorLabel);
        errorLabel.setVisible(false);
        return errorMessageBox;
    }

    private class RegistrationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String login = loginField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String repeatPassword = String.valueOf(repeatPasswordField.getPassword());
            if ("".equals(firstName) || "".equals(lastName)
                    || "".equals(login) || "".equals(password)) {
                errorLabel.setText(toHtmlErrorMessage("All fields are mandatory."));
                errorLabel.setVisible(true);
            } else if (!password.equals(repeatPassword)) {
                errorLabel.setText(toHtmlErrorMessage("Password doesn't match."));
                errorLabel.setVisible(true);
            } else {
                try {
                    model.register(firstName, lastName, login, password);
                } catch (IOException e) {
                    LOGGER.error("IOException in LoginWindowView", e);
                }
            }
        }
    }
}
