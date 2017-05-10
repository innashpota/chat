package com.shpota.chat.model.net;

import com.shpota.chat.model.PackageDispatcher;
import com.shpota.chat.model.jdbc.JdbcChatRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.shpota.chat.controller.ChatController.SERVER_PORT;

public class Server {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(SERVER_PORT);
        server.serve();
    }

    private void serve() throws IOException {
        JdbcChatRepository chatRepository = new JdbcChatRepository();
        PackageDispatcher pkgDispatcher = PackageDispatcher.construct(chatRepository);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, pkgDispatcher);
                clientHandler.setDaemon(true);
                clientHandler.start();
            }
        }
    }
}
