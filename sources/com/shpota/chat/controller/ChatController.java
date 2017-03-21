package com.shpota.chat.controller;

import com.shpota.chat.model.net.ClientModel;
import com.shpota.chat.view.LoginWindowView;

public class ChatController {
    public static void main(String[] args) {
        showView();
    }

    private static void showView() {
        LoginWindowView loginWindowView = new LoginWindowView(new ClientModel());
        loginWindowView.show();
    }
}
