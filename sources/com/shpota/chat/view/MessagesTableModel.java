package com.shpota.chat.view;

import com.shpota.chat.model.Message;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class MessagesTableModel extends AbstractTableModel {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final List<Message> messages;
    private final Map<Integer, String> userMap;

    public MessagesTableModel(List<Message> messages, Map<Integer, String> userMap) {
        super();
        if (messages == null || userMap == null) {
            throw new IllegalArgumentException(
                    "List messages, author and destination must not be null."
            );
        }
        this.messages = messages;
        this.userMap = userMap;
    }

    @Override
    public int getRowCount() {
        return messages.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Table has only one column.");
        }
        return "Conversation";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Table has only one column.");
        }
        Message message = messages.get(rowIndex);
        return toHtmlMessage(
                userMap.get(message.getAuthorId()),
                message.getMessage(),
                message.getPostedDate().format(DATE_FORMAT)
        );
    }

    private String toHtmlMessage(String author, String message, String date) {
        return "<html><i><font color = green>" + author + ", "
                + date + "</font></i><br>" + message + "</html>";
    }
}
