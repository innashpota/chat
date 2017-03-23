package com.shpota.chat.controller;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.view.LoginWindowView;

import java.io.IOException;

import static com.shpota.chat.model.net.ClientModel.initialize;
import static com.shpota.chat.model.net.Server.LOGGER;

public class ChatController {
    public static final int SERVER_PORT = 65000;
    private static final String ADDRESS = "192.168.1.102";

    public static void main(String[] args) {
        try {
            showView();
        } catch (IOException e) {
            LOGGER.error("IOException occur in ChatController.", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException occur in ChatController.", e);
        }
    }

    private static void showView() throws IOException, ClassNotFoundException {
        LoginWindowView loginWindowView = new LoginWindowView(
                initialize(SERVER_PORT, ADDRESS)
        );
        loginWindowView.show();
        ClientModel model = loginWindowView.model;
        model.serve();
    }
}
