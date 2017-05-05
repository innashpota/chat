package com.shpota.chat.model.net;

import com.shpota.chat.model.packages.*;
import com.shpota.chat.model.packages.Package;
import com.shpota.chat.view.View;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClientModel {
    private final static Logger LOGGER = Logger.getLogger(ClientModel.class);
    private ScheduledMessages scheduledMessages = new ScheduledMessages();
    private Timer time;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private List<View> views = new ArrayList<>();
    private int authorId;
    private int destinationId;

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
        LoginClientPackage loginClientPackage = new LoginClientPackage(
                login,
                password
        );
        outputStream.writeObject(loginClientPackage);
        outputStream.flush();
    }

    public void requestMessages(int authorId, int destinationId) throws IOException {
        this.authorId = authorId;
        this.destinationId = destinationId;
        if (time != null) {
            time.cancel();
            time.purge();
            scheduledMessages = new ScheduledMessages();
        }
        time = new Timer();
        time.schedule(scheduledMessages, 0, 5000);
    }

    public void addMessage(
            int authorId,
            int destinationId,
            OffsetDateTime postedDate,
            String message) throws IOException {
        AddMessageClientPackage addMessageClientPackage = new AddMessageClientPackage(
                authorId,
                destinationId,
                postedDate,
                message
        );
        outputStream.writeObject(addMessageClientPackage);
        outputStream.flush();
    }

    public void register(
            String firstName,
            String lastName,
            String login,
            String password
    ) throws IOException {
        RegistrationClientPackage registrationClientPackage = new RegistrationClientPackage(
                firstName,
                lastName,
                login,
                password
        );
        outputStream.writeObject(registrationClientPackage);
        outputStream.flush();
    }

    private class ScheduledMessages extends TimerTask {
        @Override
        public void run() {
            if (authorId > 0 && destinationId > 0) {
                RequestMessagesClientPackage messagesClientPackage = new RequestMessagesClientPackage(
                        authorId,
                        destinationId
                );
                try {
                    outputStream.writeObject(messagesClientPackage);
                    outputStream.flush();
                } catch (IOException e) {
                    LOGGER.error("Error!" + e);
                    System.exit(0);
                }
            }
        }
    }
}