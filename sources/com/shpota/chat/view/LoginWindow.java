package com.shpota.chat.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.Box.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class LoginWindow {
    public static final String IMAGE_PATH = "/images/icon.png";
    private JFrame frame;

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
        return mainBox;
    }

    private Box createLoginBox() {
        Box loginBox = createHorizontalBox();
        JLabel loginLabel = new JLabel("Login");
        JTextField loginField = new JTextField(20);
        loginBox.add(loginLabel);
        loginBox.add(createHorizontalStrut(32));
        loginBox.add(loginField);
        return loginBox;
    }

    private Box createPasswordBox() {
        Box passwordBox = createHorizontalBox();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField(20);
        passwordBox.add(passwordLabel);
        passwordBox.add(createHorizontalStrut(6));
        passwordBox.add(passwordField);
        return passwordBox;
    }

    private Box createButtonBox() {
        Box buttonBox = createHorizontalBox();
        JButton loginButton = new JButton("Login");
        JButton registrationButton = new JButton("Registration");
        buttonBox.add(createHorizontalGlue());
        buttonBox.add(loginButton);
        buttonBox.add(createHorizontalStrut(12));
        buttonBox.add(registrationButton);
        return buttonBox;
    }

    private void showView() {
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
    }
}
