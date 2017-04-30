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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static javax.swing.Box.createHorizontalBox;
import static javax.swing.Box.createVerticalBox;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MainWindowView extends View {
    private final static Logger LOGGER = Logger.getLogger(MainWindowView.class);
    private final JFrame frame;
    private final JTextArea newMessageArea = new JTextArea();
    private Box messagesBox;
    private int authorId;
    private int destinationId;

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
            authorId = ((AllUsersServerPackage) pkg).getUserId();
            Box usersBox = createUsersBox(allUsers, authorId);
            frame.add(usersBox);
            show();
        } else if (pkg instanceof MessageServerPackage) {
            List<Message> messages = ((MessageServerPackage) pkg).getMessages();
            Map<Integer, String> userMap = ((MessageServerPackage) pkg).getUserMap();
            if (messagesBox != null) {
                frame.getContentPane().remove(messagesBox);
            }
            messagesBox = createMessagesBox(messages, userMap);
            frame.add(messagesBox);
            frame.invalidate();
            frame.validate();
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
                destinationId = usersTableModel.getDestinationId(selectedRow);
                if (selectedRow != -1) {
                    try {
                        model.requestMessages(authorId, destinationId);
                    } catch (IOException e) {
                        LOGGER.error("IOException occurred in LoginWindowView.", e);
                    }
                }
            }
        });
        usersTable.setShowHorizontalLines(false);
        usersTable.setShowVerticalLines(false);
        JScrollPane usersScrollPane = new JScrollPane(usersTable);
        usersScrollPane.getViewport().setBackground(usersTable.getBackground());
        return usersScrollPane;
    }

    private Box createMessagesBox(List<Message> messages, Map<Integer, String> userMap) {
        Box messagesBox = createVerticalBox();
        messagesBox.setBorder(new EmptyBorder(12, 300, 12, 12));
        JScrollPane messagesScrollPane = createMessagesScrollPane(messages, userMap);
        messagesBox.add(messagesScrollPane);
        messagesBox.add(createNewMessageBox());
        return messagesBox;
    }

    private JScrollPane createMessagesScrollPane(List<Message> messages, Map<Integer, String> userMap) {
        MessagesTableModel messagesTableModel = new MessagesTableModel(messages, userMap);
        JTable messagesTable = new JTable(messagesTableModel);
        messagesTable.getColumnModel().getColumn(0).setCellRenderer(
                new MessagesRenderer()
        );
        messagesTable.setShowHorizontalLines(false);
        JScrollPane messagesScrollPane = new JScrollPane(messagesTable);
        messagesScrollPane.getViewport().setBackground(messagesTable.getBackground());
        return messagesScrollPane;
    }

    private Box createNewMessageBox() {
        Box newMessageBox = createHorizontalBox();
        newMessageBox.setBorder(new EmptyBorder(6, 0, 0, 0));
        newMessageArea.setLineWrap(true);
        newMessageArea.setWrapStyleWord(true);
        newMessageBox.add(newMessageArea);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new SendActionListener());
        newMessageBox.add(sendButton);
        return newMessageBox;
    }

    private class SendActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String newMessage = newMessageArea.getText();
            OffsetDateTime postedDate = OffsetDateTime.now();
            if (!"".equals(newMessage)) {
                try {
                    model.addMessage(authorId, destinationId, postedDate, newMessage);
                    newMessageArea.setText("");
                } catch (IOException e) {
                    LOGGER.error("IOException occurred in MainWindowView.", e);
                }
            }
        }
    }
}
