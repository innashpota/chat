package com.shpota.chat.model.jdbc;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.exceptions.RepositoryException;

import java.sql.*;
import java.util.List;

public class JdbcChatRepository implements ChatRepository {
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (first_name, last_name, login, password) " +
                    "VALUES (?, ?, ?, ?);";

    private static final String SQL_SELECT_USER_ID =
            "SELECT user_id FROM users WHERE login = ?;";

    private static final String SQL_UPDATE_USER =
            "UPDATE users SET first_name = ?, last_name = ?, login = ?, password = ?" +
                    "WHERE user_id = ?;";

    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE user_id = ?;";


    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public int addUser(User user) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

        return user.getId();
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            deleteStatement.setInt(1, userId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateUser(int userId, User user) {

    }

    @Override
    public User loginUser(String login, String password) {
        return null;
    }

    @Override
    public User getUser(int userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void addMessage(Message message) {

    }

    @Override
    public List<Message> getMessages(int firstId, int secondId) {
        return null;
    }
}
