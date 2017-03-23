package com.shpota.chat.model.net;

import com.shpota.chat.model.jdbc.JdbcChatRepository;

import java.net.*;
import java.io.*;

import org.apache.log4j.Logger;

import static com.shpota.chat.controller.ChatController.SERVER_PORT;

public class Server {
    public final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.serve();
    }

    private void serve() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            JdbcChatRepository chatRepository = new JdbcChatRepository();

            while (true) {
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket, chatRepository);
                clientHandler.setDaemon(true);
                clientHandler.start();
            }
        }
    }
}
