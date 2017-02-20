package com.shpota.chat.model.net;

import com.shpota.chat.model.ChatRepository;
import com.shpota.chat.model.packages.ClientLoginPackage;
import com.shpota.chat.model.packages.ClientAddMessagePackage;
import com.shpota.chat.model.packages.ClientRegistrationPackage;
import com.shpota.chat.model.strategies.LoginStrategy;
import com.shpota.chat.model.strategies.RegistrationStrategy;

import java.io.*;
import java.net.Socket;

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
                    if (object instanceof ClientRegistrationPackage) {
                        ClientRegistrationPackage registration = (ClientRegistrationPackage) object;
                        RegistrationStrategy registrationStrategy =
                                new RegistrationStrategy(chatRepository);
                        outputStream.writeObject(registrationStrategy.handle(registration));
                    } else if (object instanceof ClientLoginPackage) {
                        ClientLoginPackage login = (ClientLoginPackage) object;
                        LoginStrategy loginStrategy = new LoginStrategy(chatRepository);
                        outputStream.writeObject(loginStrategy.handle(login));
                    } else if (object instanceof ClientAddMessagePackage) {
                        ClientAddMessagePackage message = (ClientAddMessagePackage) object;
                        outputStream.writeObject(
                                "Message: " + message.getMessage()
                        );
                    }
                    outputStream.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
