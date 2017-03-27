package com.shpota.chat.view;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.Package;

import javax.swing.*;
import java.awt.*;

import static javax.swing.Box.createVerticalBox;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainWindowView extends View {
    private JFrame frame;

    public MainWindowView(ClientModel model) {
        super(model);
        this.frame = createMainFrame();
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
            show();
        } else {
            hide();
        }
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Chat");
        frame.setIconImage(getIconImage());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setContentPane(createMainBox());
        frame.setResizable(false);
        return frame;
    }

    private Box createMainBox() {
        Box mainBox = createVerticalBox();
        return mainBox;
    }
}
