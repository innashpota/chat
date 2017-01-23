package com.shpota.chat.model;

import com.shpota.chat.model.jdbc.JdbcChatRepository;

public class Application {

    public static void main(String[] arg) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        JdbcChatRepository jdbcChatRepository = new JdbcChatRepository();
        System.out.println(
                jdbcChatRepository.addUser(new User("Us", "Us", "us", "1"))
        );
        //jdbcChatRepository.deleteUser(18);
        //jdbcChatRepository.updateUser(16, new User("New user", "User", "user", "1"));
    }
}
