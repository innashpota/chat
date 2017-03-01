package com.shpota.chat.model.net;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.packages.*;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.model.strategies.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static com.shpota.chat.model.net.Server.LOGGER;

public class ClientHandler extends Thread {
    private final Map<Class, Strategy> dispatch = new HashMap<Class, Strategy>();
    private final Socket clientSocket;
    private final ChatRepository chatRepository;

    public ClientHandler(Socket clientSocket, ChatRepository chatRepository) {
        this.clientSocket = clientSocket;
        this.chatRepository = chatRepository;
    }

    private Map<Class, Strategy> handleMap(ChatRepository chatRepository) {
        dispatch.put(
                RegistrationClientPackage.class,
                new RegistrationStrategy(chatRepository)
        );
        dispatch.put(
                LoginClientPackage.class,
                new LoginStrategy(chatRepository)
        );
        dispatch.put(
                RegistrationClientPackage.class,
                new RequestMessagesStrategy(chatRepository)
        );
        dispatch.put(
                AddMessageClientPackage.class,
                new AddMessageStrategy(chatRepository)
        );
        return dispatch;
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
                    Strategy strategy = handleMap(chatRepository).get(object.getClass());
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
