package com.shpota.chat.model;

import com.shpota.chat.model.jdbc.JdbcChatRepository;

import java.time.OffsetDateTime;

public class Application {

    public static void main(String[] arg) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        JdbcChatRepository jdbcChatRepository = new JdbcChatRepository();
        System.out.println(
                jdbcChatRepository.getMessages(1, 2)
        );
    }
}
