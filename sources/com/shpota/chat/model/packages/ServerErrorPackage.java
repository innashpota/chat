package com.shpota.chat.model.packages;

public class ServerErrorPackage extends Package {
    private final String description;

    public ServerErrorPackage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
