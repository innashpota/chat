package com.shpota.chat.model.packages;

public class RequestMessagesClientPackage extends Package{
    private final String loginAuthor;
    private final String loginDestination;

    public RequestMessagesClientPackage(String loginAuthor, String loginDestination) {
        this.loginAuthor = loginAuthor;
        this.loginDestination = loginDestination;
    }

    public String getLoginAuthor() {
        return loginAuthor;
    }

    public String getLoginDestination() {
        return loginDestination;
    }
}
