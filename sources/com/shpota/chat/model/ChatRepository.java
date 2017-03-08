package com.shpota.chat.model;

import java.util.List;

public interface ChatRepository {

    int addUser(User user);

    void deleteUser(int userId);

    void updateUser(int userId, User user);

    User loginUser(String login, String password);

    User getUser(int userId);

    List<User> getAllUsers();

    User getUserByLogin(String login);

    void addMessage(Message message);

    List<Message> getMessages(int firstId, int secondId);
}
