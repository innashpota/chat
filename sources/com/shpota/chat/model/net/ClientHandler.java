package com.shpota.chat.model.net;

import com.shpota.chat.model.PackageFactory;
import com.shpota.chat.model.jdbc.JdbcChatRepository;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.strategies.Strategy;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final static Logger LOGGER = Logger.getLogger(ClientHandler.class);
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
                        Package pkg = strategy.handle((Package) object);
                        outputStream.writeObject(pkg);
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
