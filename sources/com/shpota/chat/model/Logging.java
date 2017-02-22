package com.shpota.chat.model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Logging {
    public static final Logger LOGGER = Logger.getLogger(Logging.class.getName());

    public void createLogFile() {
        try {
            FileHandler handler = new FileHandler("./logging.log");
            LOGGER.addHandler(handler);
        } catch (IOException e) {
            System.err.println(
                    "Could not setup logger configuration: " + e.toString()
            );
        }
    }
}
