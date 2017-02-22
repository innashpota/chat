package com.shpota.chat.model.net;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.packages.LoginClientPackage;
import com.shpota.chat.model.packages.AddMessageClientPackage;
import com.shpota.chat.model.packages.RegistrationClientPackage;
import com.shpota.chat.model.packages.RequestMessagesClientPackage;
import com.shpota.chat.model.strategies.LoginStrategy;
import com.shpota.chat.model.strategies.AddMessageStrategy;
import com.shpota.chat.model.strategies.RegistrationStrategy;
import com.shpota.chat.model.strategies.RequestMessagesStrategy;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;

import static com.shpota.chat.model.Logging.LOGGER;

public class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final ChatRepository chatRepository;

    public ClientHandler(Socket clientSocket, ChatRepository chatRepository) {
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
                    if (object instanceof RegistrationClientPackage) {
                        RegistrationClientPackage registration =
                                (RegistrationClientPackage) object;
                        RegistrationStrategy registrationStrategy =
                                new RegistrationStrategy(chatRepository);
                        outputStream.writeObject(registrationStrategy.handle(registration));
                    } else if (object instanceof LoginClientPackage) {
                        LoginClientPackage login = (LoginClientPackage) object;
                        LoginStrategy loginStrategy = new LoginStrategy(chatRepository);
                        outputStream.writeObject(loginStrategy.handle(login));
                    } else if (object instanceof RequestMessagesClientPackage) {
                        RequestMessagesClientPackage selectReceiver =
                                (RequestMessagesClientPackage) object;
                        RequestMessagesStrategy receiverStrategy =
                                new RequestMessagesStrategy(chatRepository);
                        outputStream.writeObject(receiverStrategy.handle(selectReceiver));
                    } else if (object instanceof AddMessageClientPackage) {
                        AddMessageClientPackage message = (AddMessageClientPackage) object;
                        AddMessageStrategy addMessageStrategy = new AddMessageStrategy(
                                chatRepository
                        );
                        outputStream.writeObject(addMessageStrategy.handle(message));
                    }
                    outputStream.flush();
                } catch (ClassNotFoundException e) {
                    LOGGER.log(
                            Level.SEVERE,
                            "ClassNotFoundException occur in ClientHandler.",
                            e
                    );
                }
            }
        } catch (IOException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "IOException occur in ClientHandler.",
                    e
            );
        }
    }
}
