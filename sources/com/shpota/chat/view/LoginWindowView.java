package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.ErrorServerPackage;
import com.shpota.chat.model.packages.Package;
import org.apache.log4j.Logger;

import javax.swing.Box;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.Box.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class LoginWindowView extends View {
    private final static Logger LOGGER = Logger.getLogger(LoginWindowView.class);
    private final JFrame frame;
    private final JTextField loginField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel errorLabel = new JLabel();
    private Box loginMainBox;

    public LoginWindowView(ClientModel model) {
        super(model);
        this.frame = createLoginFrame();
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }

    @Override
    public void onPackageReceived(Package pkg) {
        if (pkg instanceof AllUsersServerPackage) {
            hide();
        } else if (pkg instanceof ErrorServerPackage) {
            errorLabel.setVisible(true);
        }
    }

    private JFrame createLoginFrame() {
        JFrame frame = new JFrame("Sign up");
        frame.setIconImage(getIconImage());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        loginMainBox = createLoginMainBox();
        frame.setContentPane(loginMainBox);
        frame.setResizable(false);
        return frame;
    }

    private Box createLoginMainBox() {
        Box mainBox = createVerticalBox();
        mainBox.setBorder(new EmptyBorder(50, 200, 400, 200));
        mainBox.add(createLoginBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createPasswordBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(createLoginButtonBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(errorMessageBox());
        mainBox.add(createVerticalStrut(30));
        return mainBox;
    }

    private Box createLoginBox() {
        Box loginBox = createHorizontalBox();
        JLabel loginLabel = new JLabel("Login");
        loginBox.add(loginLabel);
        loginBox.add(createHorizontalStrut(32));
        loginBox.add(loginField);
        return loginBox;
    }

    private Box createPasswordBox() {
        Box passwordBox = createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password");
        passwordBox.add(passwordLabel);
        passwordBox.add(createHorizontalStrut(6));
        passwordBox.add(passwordField);
        return passwordBox;
    }

    private Box createLoginButtonBox() {
        Box buttonBox = createHorizontalBox();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginActionListener());
        JButton registrationButton = new JButton("Registration");
        registrationButton.addActionListener(new RegistrationActionListener());
        buttonBox.add(createHorizontalGlue());
        buttonBox.add(loginButton);
        buttonBox.add(createHorizontalStrut(12));
        buttonBox.add(registrationButton);
        return buttonBox;
    }

    private Box errorMessageBox() {
        Box errorMessageBox = createHorizontalBox();
        errorMessageBox.add(errorLabel);
        errorLabel.setVisible(false);
        return errorMessageBox;
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String login = loginField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if ("".equals(login) || "".equals(password)) {
                errorLabel.setText(toHtmlErrorMessage(
                        "The login or password you’ve " +
                                "entered doesn’t match any account."
                ));
                errorLabel.setVisible(true);
            } else {
                try {
                    model.login(login, password);
                } catch (IOException e) {
                    LOGGER.error("IOException occurred in LoginWindowView", e);
                }
            }
        }
    }

    private class RegistrationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            loginMainBox.setVisible(false);
            RegistrationBoxView boxView = new RegistrationBoxView(model, frame);
            frame.setContentPane(boxView.registrationBox);
        }
    }
}
