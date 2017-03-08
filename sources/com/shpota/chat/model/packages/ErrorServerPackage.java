package com.shpota.chat.model.packages;

public class ErrorServerPackage extends Package {
    private final String description;

    public ErrorServerPackage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
