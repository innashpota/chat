package com.shpota.chat.controller;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.view.LoginWindowView;
import com.shpota.chat.view.MainWindowView;
import org.apache.log4j.Logger;

import java.io.IOException;

import static com.shpota.chat.model.net.ClientModel.initialize;

public class ChatController {
    private final static Logger LOGGER = Logger.getLogger(ChatController.class);
    public static final int SERVER_PORT = 65000;
    private static final String ADDRESS = "localhost";//"192.168.1.102";

    public static void main(String[] args) throws IOException {
        try {
            showView();
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException occurred in ChatController.", e);
        }
    }

    private static void showView() throws IOException, ClassNotFoundException {
        ClientModel model = initialize(SERVER_PORT, ADDRESS);
        LoginWindowView loginWindowView = new LoginWindowView(model);
        MainWindowView mainWindowView = new MainWindowView(model);
        loginWindowView.show();
        model.serve();
    }
}
