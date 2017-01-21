package com.shpota.chat.model.jdbc;

import com.shpota.chat.model.Message;
import com.shpota.chat.model.User;

import java.util.List;

public interface ChatRepository {

    int addUser(User user);

    void deleteUser(int userId);

    void updateUser(int userId, User user);

    User loginUser(String login, String password);

    void addMessage(Message message);

    List<Message> getMessages(int firstId, int secondId);
}
