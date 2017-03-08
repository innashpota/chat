package com.shpota.chat.model.net;

import com.shpota.chat.model.packages.LoginClientPackage;
import com.shpota.chat.model.packages.AllUsersServerPackage;
import com.shpota.chat.model.packages.ErrorServerPackage;

import java.net.*;
import java.io.*;

public class Client {
    private static final int SERVER_PORT = 65000;
    private static final String ADDRESS = "192.168.1.102";

    public static void main(String[] args) throws UnknownHostException {
        InetAddress ipAddress = InetAddress.getByName(ADDRESS);
        System.out.println(
                "Any of you heard of a socket with IP address " + ADDRESS +
                        " and port " + SERVER_PORT + "?"
        );

        try (Socket socket = new Socket(ipAddress, SERVER_PORT)) {

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            System.out.println("Type your login and press enter");
            System.out.println();

            while (true) {
                String login = bufferedReader.readLine();
                objectOutputStream.writeObject(
                        new LoginClientPackage(login, "1")
                );
                /*objectOutputStream.writeObject(
                        new RegistrationClientPackage(
                                "Vasay",
                                "Pupkin",
                                login,
                                "1")
                );*/
                objectOutputStream.flush();
                Object readObject = objectInputStream.readObject();
                if (readObject instanceof AllUsersServerPackage) {
                    AllUsersServerPackage usersPackage =
                            (AllUsersServerPackage) readObject;
                    System.out.println(usersPackage.toString());
                } else if (readObject instanceof ErrorServerPackage) {
                    ErrorServerPackage errorPackage =
                            (ErrorServerPackage) readObject;
                    System.out.println(errorPackage.getDescription());
                }
                Thread.sleep(2000);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}