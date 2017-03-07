package com.shpota.chat.model.net;

import com.shpota.chat.model.PackageFactory;
import com.shpota.chat.model.jdbc.JdbcChatRepository;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.strategies.*;

import java.io.*;
import java.net.Socket;

import static com.shpota.chat.model.net.Server.LOGGER;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final JdbcChatRepository chatRepository;

    public ClientHandler(Socket clientSocket, JdbcChatRepository chatRepository) {
        this.clientSocket = clientSocket;
        this.chatRepository = chatRepository;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(
                        clientSocket.getOutputStream()
                );
                ObjectInputStream inputStream = new ObjectInputStream(
                        clientSocket.getInputStream()
                )
        ) {
            while (true) {
                try {
                    Object object = inputStream.readObject();
                    Strategy strategy = new PackageFactory().getStrategy(
                            object.getClass(),
                            chatRepository
                    );
                    if (strategy != null) {
                        outputStream.writeObject(strategy.handle((Package) object));
                    }
                    outputStream.flush();
                } catch (ClassNotFoundException e) {
                    LOGGER.error("ClassNotFoundException occur in ClientHandler.", e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("IOException occur in ClientHandler.", e);
        }
    }
}
