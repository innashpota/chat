package com.shpota.chat.model.packages;

import java.time.OffsetDateTime;

public class ClientAddMessagePackage extends Package {
    private final int authorID;
    private final int destinationID;
    private final OffsetDateTime postedDate;
    private final String message;

    public ClientAddMessagePackage(
            int authorID,
            int destinationID,
            OffsetDateTime postedDate,
            String message
    ) {
        this.authorID = authorID;
        this.destinationID = destinationID;
        this.postedDate = postedDate;
        this.message = message;
    }

    public int getAuthorID() {
        return authorID;
    }

    public int getDestinationID() {
        return destinationID;
    }


    public OffsetDateTime getPostedDate() {
        return postedDate;
    }

    public String getMessage() {
        return message;
    }
}
