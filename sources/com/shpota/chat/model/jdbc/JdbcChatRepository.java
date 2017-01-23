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

    private static final String SQL_UPDATE_USER =
            "UPDATE users SET first_name = ?, last_name = ?, password = ?" +
                    "WHERE user_id = ?;";

    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE user_id = ?;";

    private static final String SQL_SELECT_USER =
            "SELECT first_name,last_name, login, password FROM users " +
                    "WHERE user_id = ?;";


    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public int addUser(User user) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement addStatement =
                     connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            addStatement.setString(1, user.getFirstName());
            addStatement.setString(2, user.getLastName());
            addStatement.setString(3, user.getLogin());
            addStatement.setString(4, user.getPassword());
            addStatement.executeUpdate();
            try (ResultSet resultSet = addStatement.getGeneratedKeys()) {
                int id = -1;
                if (resultSet.next()) {
                    id = resultSet.getInt("user_id");
                }
                return id;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void deleteUser(int userId) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement deleteStatement =
                     connection.prepareStatement(SQL_DELETE_USER)) {
            deleteStatement.setInt(1, userId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateUser(int userId, User user) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement updateStatement =
                     connection.prepareStatement(SQL_UPDATE_USER)) {
            updateStatement.setString(1, user.getFirstName());
            updateStatement.setString(2, user.getLastName());
            updateStatement.setString(3, user.getPassword());
            updateStatement.setInt(4, userId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User loginUser(String login, String password) {
        return null;
    }

    @Override
    public User getUser(int userId) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_USER)) {
            selectStatement.setInt(1, userId);
            try (ResultSet resultSet = selectStatement.executeQuery()){
                User user = null;
                if(resultSet.next()){
                    user = new User(
                            userId,
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                    );
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
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
