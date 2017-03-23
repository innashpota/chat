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
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private List<View> views = new ArrayList<>();

    private ClientModel(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public static ClientModel initialize(int port, String host) throws IOException {
        InetAddress ipAddress = InetAddress.getByName(host);
        Socket socket = new Socket(ipAddress, port);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        return new ClientModel(objectOutputStream, objectInputStream);
    }

    public void addView(View view) {
        views.add(view);
    }

    private void notifyViews(Package pkg) {
        for (View view : views) {
            view.onPackageReceived(pkg);
        }
    }

    public void serve() throws IOException, ClassNotFoundException {
        while (true) {
            Object pkg = inputStream.readObject();
            if (pkg instanceof Package) {
                notifyViews((Package) pkg);
            } else {
                LOGGER.error("Unknown package: " + pkg);
            }
        }
    }

    public void login(String login, String password) throws IOException {
        LoginClientPackage loginClientPackage = new LoginClientPackage(login, password);
        outputStream.writeObject(loginClientPackage);
        outputStream.flush();
    }
}