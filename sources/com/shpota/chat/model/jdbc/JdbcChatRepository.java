package com.shpota.chat.model.jdbc;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;
import com.shpota.chat.model.exceptions.RepositoryException;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcChatRepository implements ChatRepository {
    private static final String SQL_INSERT_USER =
            "INSERT INTO users (first_name, last_name, login, password) " +
                    "VALUES (?, ?, ?, ?);";

    private static final String SQL_UPDATE_USER =
            "UPDATE users " +
                    "SET first_name = ?, last_name = ?, password = ?" +
                    "WHERE user_id = ?;";

    private static final String SQL_DELETE_USER =
            "DELETE FROM users " +
                    "WHERE user_id = ?;";

    private static final String SQL_SELECT_USER =
            "SELECT first_name,last_name, login, password " +
                    "FROM users " +
                    "WHERE user_id = ?;";

    private static final String SQL_SELECT_LOGIN_USER =
            "SELECT user_id, first_name, last_name " +
                    "FROM users " +
                    "WHERE login = ? AND password = ?;";

    private static final String SQL_SELECT_ALL_USERS =
            "SELECT * " +
                    "FROM users " +
                    "ORDER BY user_id;";

    private static final String SQL_SELECT_USER_BY_LOGIN =
            "SELECT * " +
                    "FROM users " +
                    "WHERE login = ?;";

    private static final String SQL_ADD_MESSAGE =
            "INSERT INTO message (author_id, destination_id, posted_date, message_text) " +
                    "VALUES (?, ?, ?, ?);";

    private static final String SQL_SELECT_ALL_MESSAGE =
            "SELECT * " +
                    "FROM message " +
                    "WHERE author_id = ? AND destination_id = ? " +
                    "OR author_id = ? AND destination_id = ?" +
                    "ORDER BY posted_date;";

    private ConnectionManager connectionManager = new ConnectionManager();

    @Override
    public int addUser(User user) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement addStatement = connection.prepareStatement(
                     SQL_INSERT_USER,
                     Statement.RETURN_GENERATED_KEYS
             )) {
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
             PreparedStatement deleteStatement = connection.prepareStatement(
                     SQL_DELETE_USER
             )) {
            deleteStatement.setInt(1, userId);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void updateUser(int userId, User user) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement updateStatement = connection.prepareStatement(
                     SQL_UPDATE_USER
             )) {
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
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement loginStatement = connection.prepareStatement(
                     SQL_SELECT_LOGIN_USER
             )) {
            loginStatement.setString(1, login);
            loginStatement.setString(2, password);
            try (ResultSet resultSet = loginStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            login,
                            password
                    );
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User getUser(int userId) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SQL_SELECT_USER
             )) {
            selectStatement.setInt(1, userId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                User user = null;
                if (resultSet.next()) {
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
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement allUsersStatement = connection.prepareStatement(
                     SQL_SELECT_ALL_USERS
             )) {
            try (ResultSet resultSet = allUsersStatement.executeQuery()) {
                List<User> usersList = new ArrayList<>();
                while (resultSet.next()) {
                    usersList.add(new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                    ));
                }
                return usersList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement getUserByLoginStatement = connection.prepareStatement(
                     SQL_SELECT_USER_BY_LOGIN
             )) {
            getUserByLoginStatement.setString(1, login);
            try (ResultSet resultSet = getUserByLoginStatement.executeQuery()) {
                User user = null;
                while (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("user_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            login,
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
    public void addMessage(Message message) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement addMessageStatement = connection.prepareStatement(
                     SQL_ADD_MESSAGE
             )) {
            addMessageStatement.setInt(1, message.getAuthorId());
            addMessageStatement.setInt(2, message.getDestinationId());
            addMessageStatement.setObject(3, message.getPostedDate());
            addMessageStatement.setString(4, message.getMessage());
            addMessageStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Message> getMessages(int firstId, int secondId) {
        try (Connection connection = connectionManager.openConnection();
             PreparedStatement allMessageStatement =
                     connection.prepareStatement(SQL_SELECT_ALL_MESSAGE)) {
            allMessageStatement.setInt(1, firstId);
            allMessageStatement.setInt(2, secondId);
            allMessageStatement.setInt(3, secondId);
            allMessageStatement.setInt(4, firstId);
            try (ResultSet resultSet = allMessageStatement.executeQuery()) {
                List<Message> messageList = new ArrayList<>();
                while (resultSet.next()) {
                    OffsetDateTime postedDate = resultSet.getObject(
                            "posted_date", OffsetDateTime.class
                    );
                    messageList.add(new Message(
                            resultSet.getInt("message_id"),
                            resultSet.getInt("author_id"),
                            resultSet.getInt("destination_id"),
                            postedDate,
                            resultSet.getString("message_text")
                    ));
                }
                return messageList;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
