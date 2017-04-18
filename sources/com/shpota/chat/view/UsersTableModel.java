package com.shpota.chat.view;

import com.shpota.chat.model.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsersTableModel extends AbstractTableModel {
    private final List<User> allUsers;

    public UsersTableModel(List<User> allUsers) {
        super();
        if (allUsers == null) {
            throw new IllegalArgumentException("List users must not be null.");
        }
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
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Table has only one column.");
        }
        return "Users";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex != 0) {
            throw new IllegalArgumentException("Table has only one column.");
        }
        User user = allUsers.get(rowIndex);
        return user.getFirstName() + " " + user.getLastName();
    }

    public int getDestinationId(int index) {
        if (index <= 0) {
            throw new IllegalArgumentException("Index must be positive.");
        }
        User user = allUsers.get(index);
        return user.getId();
    }
}
