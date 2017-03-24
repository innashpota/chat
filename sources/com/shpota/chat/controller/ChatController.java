package com.shpota.chat.controller;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.view.LoginWindowView;

import java.io.IOException;

import static com.shpota.chat.model.net.ClientModel.initialize;
import static com.shpota.chat.model.net.Server.LOGGER;

public class ChatController {
    public static final int SERVER_PORT = 65000;
    private static final String ADDRESS = "localhost";//"192.168.1.102";

    public static void main(String[] args) {
        try {
            showView();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Exception occur in ChatController.", e);
        }
    }

    private static void showView() throws IOException, ClassNotFoundException {
        ClientModel model = initialize(SERVER_PORT, ADDRESS);
        LoginWindowView loginWindowView = new LoginWindowView(model);
        loginWindowView.show();
        model.serve();
    }
}
