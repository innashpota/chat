package com.shpota.chat.view;

import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.MessageServerPackage;
import com.shpota.chat.model.packages.Package;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import static javax.swing.Box.createVerticalBox;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainWindowView extends View {
    private final static Logger LOGGER = Logger.getLogger(MainWindowView.class);
    private final JFrame frame;

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
            List<User> allUsers = ((AllUsersServerPackage) pkg).getAllUsers();
            int authorId = ((AllUsersServerPackage) pkg).getUserId();
            frame.add(createUsersBox(allUsers, authorId));
            show();
        } else if (pkg instanceof MessageServerPackage) {
            List<Message> messages = ((MessageServerPackage) pkg).getMessages();
            Box messagesBox = createMessagesBox(messages);
            frame.add(messagesBox);
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
        frame.setResizable(false);
        frame.setVisible(false);
        return frame;
    }

    private Box createUsersBox(List<User> allUsers, int authorId) {
        Box usersBox = createVerticalBox();
        usersBox.setBorder(new EmptyBorder(12, 12, 12, 500));
        JScrollPane usersScrollPane = createUsersScrollPane(allUsers, authorId);
        usersBox.add(usersScrollPane);
        return usersBox;
    }

    private JScrollPane createUsersScrollPane(List<User> allUsers, int authorId) {
        UsersTableModel usersTableModel = new UsersTableModel(allUsers);
        JTable usersTable = new JTable(usersTableModel);
        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int selectedRow = usersTable.getSelectedRow();
                int destinationId = usersTableModel.getDestinationId(selectedRow);
                try {
                    model.requestMessages(authorId, destinationId);
                } catch (IOException e) {
                    LOGGER.error("IOException occur in LoginWindowView.", e);
                }
            }
        });
        usersTable.setShowHorizontalLines(false);
        usersTable.setShowVerticalLines(false);
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersScrollPane.getViewport().setBackground(usersTable.getBackground());
        return usersScrollPane;
    }

    private Box createMessagesBox(List<Message> messages) {
        Box messagesBox = createVerticalBox();
        messagesBox.setBorder(new EmptyBorder(12, 100, 12, 12));
        JScrollPane messagesScrollPane = createMessagesScrollPane(messages);
        messagesBox.add(messagesScrollPane);
        return messagesBox;
    }

    private JScrollPane createMessagesScrollPane(List<Message> messages) {
        MessagesTableModel messagesTableModel = new MessagesTableModel(messages);
        JTable messagesTable = new JTable(messagesTableModel);
        JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
        messagesScrollPane.getViewport().setBackground(messagesTable.getBackground());
        return messagesScrollPane;
    }
}
