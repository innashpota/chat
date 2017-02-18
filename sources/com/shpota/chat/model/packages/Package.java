package com.shpota.chat.model.packages;

import java.io.Serializable;

public abstract class Package implements Serializable{
    private int authorId;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
