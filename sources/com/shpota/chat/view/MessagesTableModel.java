package com.shpota.chat.view;

import com.shpota.chat.model.Message;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MessagesTableModel extends AbstractTableModel {
    private List<Message> messages;

    public MessagesTableModel(List<Message> messages) {
        super();
        this.messages = messages;
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
        String columnTitle;
        if (columnIndex == 0) {
            columnTitle = "Messages";
        } else {
            throw new IllegalArgumentException("Chat is inaccessible.");
        }

        return columnTitle;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Message message = messages.get(rowIndex);
        String columnValue;
        if (columnIndex == 0) {
            columnValue = message.getAuthorId() + "/n" +
                    message.getMessage() + "/n" +
                    message.getPostedDate();
        } else {
            throw new IllegalArgumentException("Table has only two columns.");
        }
        return columnValue;
    }
}
