package com.shpota.chat.view;

import com.shpota.chat.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsersTableModel extends AbstractTableModel {
    private List<User> allUsers;

    public UsersTableModel(List<User> allUsers) {
        super();
        this.allUsers = allUsers;
    }

    @Override
    public int getRowCount() {
        return allUsers.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        String columnTitle;
        if (columnIndex == 0) {
            columnTitle = "Users";
        } else {
            throw new IllegalArgumentException("Table has only one column.");
        }
        return columnTitle;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = allUsers.get(rowIndex);
        String columnValue;
        if (columnIndex == 0) {
            columnValue = user.getFirstName() + " " + user.getLastName();
        } else {
            throw new IllegalArgumentException("Table has only one column.");
        }
        return columnValue;
    }

    public int getDestinationId(int index) {
        User user = allUsers.get(index);
        return user.getId();
    }
}
