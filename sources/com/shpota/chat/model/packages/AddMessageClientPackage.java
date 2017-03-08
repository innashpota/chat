package com.shpota.chat.model.packages;

import java.time.OffsetDateTime;

public class AddMessageClientPackage extends Package {
    private final int authorId;
    private final int destinationId;
    private final OffsetDateTime postedDate;
    private final String message;

    public AddMessageClientPackage(
            int authorId,
            int destinationId,
            OffsetDateTime postedDate,
            String message
    ) {
        if (authorId <= 0 || destinationId <=0 || postedDate == null || message == null) {
            throw new IllegalArgumentException(
                    "Author, destination, date and message must not be null."
            );
        }
        this.authorId = authorId;
        this.destinationId = destinationId;
        this.postedDate = postedDate;
        this.message = message;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public OffsetDateTime getPostedDate() {
        return postedDate;
    }

    public String getMessage() {
        return message;
    }
}
