package com.shpota.chat.model.net;

import com.shpota.chat.model.packages.LoginClientPackage;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.view.View;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.shpota.chat.model.net.Server.LOGGER;

public class ClientModel {
    public static final int SERVER_PORT = 65000;
    private static final String ADDRESS = "192.168.1.102";
    private List<View> views = new ArrayList<>();

    public void addView(View view) {
        views.add(view);
    }

    public void notifyViews(Package pkg) {
        for (View view : views) {
            view.onPackageReceived(pkg);
        }
    }

    public void login(String login, String password) {
        try {
            run(new LoginClientPackage(login, password));
        } catch (UnknownHostException e) {
            LOGGER.error("UnknownHostException occur in ClientModel.", e);
        }
    }

    private void run(Package pkg) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getByName(ADDRESS);
        LOGGER.info(
                "Any of you heard of a socket with IP address " + ADDRESS +
                        " and port " + SERVER_PORT + "?"
        );

        try (Socket socket = new Socket(ipAddress, SERVER_PORT)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            while (true) {
                objectOutputStream.writeObject(pkg);
                objectOutputStream.flush();
                Object readObject = objectInputStream.readObject();
                notifyViews((Package) readObject);
            }
        } catch (Exception x) {
            LOGGER.error("Exception occur in ClientModel.", x);
        }
    }
}