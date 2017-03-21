package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.ErrorServerPackage;
import com.shpota.chat.model.packages.Package;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Arrays;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.util.stream.Collectors.*;
import static javax.swing.Box.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class LoginWindowView extends View {
    public static final String IMAGE_PATH = "/images/icon.png";
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public LoginWindowView(ClientModel model) {
        super(model);
    }

    public void show() {
        createFrame();
        showView();
    }

    private void createFrame() {
        frame = new JFrame("Sign up");
        frame.setIconImage(getDefaultToolkit().getImage(getClass().getResource(IMAGE_PATH)));
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(createMainBox());
    }

    private Box createMainBox() {
        Box mainBox = createVerticalBox();
        mainBox.setBorder(new EmptyBorder(12, 12, 12, 12));
        mainBox.add(createLoginBox());
        mainBox.add(createVerticalStrut(12));
        mainBox.add(createPasswordBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(createButtonBox());
        mainBox.add(createVerticalStrut(17));
        mainBox.add(errorMessageBox());
        mainBox.add(createVerticalStrut(30));
        return mainBox;
    }

    private Box createLoginBox() {
        Box loginBox = createHorizontalBox();
        JLabel loginLabel = new JLabel("Login");
        loginField = new JTextField(20);
        loginBox.add(loginLabel);
        loginBox.add(createHorizontalStrut(32));
        loginBox.add(loginField);
        return loginBox;
    }

    private Box createPasswordBox() {
        Box passwordBox = createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField(20);
        passwordBox.add(passwordLabel);
        passwordBox.add(createHorizontalStrut(6));
        passwordBox.add(passwordField);
        return passwordBox;
    }

    private Box createButtonBox() {
        Box buttonBox = createHorizontalBox();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(actionEvent -> {
            String login = loginField.getText();
            String password = String.valueOf(passwordField.getPassword());
            model.login(login, password);
        });
        JButton registrationButton = new JButton("Registration");
        buttonBox.add(createHorizontalGlue());
        buttonBox.add(loginButton);
        buttonBox.add(createHorizontalStrut(12));
        buttonBox.add(registrationButton);
        return buttonBox;
    }

    private Box errorMessageBox() {
        Box errorMessageBox = createHorizontalBox();
        errorLabel = new JLabel(toHtmlRedErrorMessage(
                "The login or password number you’ve",
                "entered doesn’t match any account."
        ));
        errorMessageBox.add(errorLabel);
        errorLabel.setVisible(false);
        return errorMessageBox;
    }

    private String toHtmlRedErrorMessage(String... messages) {
        String str = Arrays.stream(messages).collect(joining("<br>"));
        return "<html><font color = red><i>" + str + "</i></font></html>";
    }

    private void showView() {
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }

    @Override
    public void onPackageReceived(Package pkg) {
        if (pkg instanceof AllUsersServerPackage) {
            frame.setVisible(false);
        } else if (pkg instanceof ErrorServerPackage) {
            new LoginWindowView(model);
            errorLabel.setVisible(true);
        }
    }
}
