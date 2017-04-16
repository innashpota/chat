package com.shpota.chat.model.packages;

public class RequestMessagesClientPackage extends Package {
    private final int authorId;
    private final int destinationId;

    public RequestMessagesClientPackage(int authorId, int destinationId) {
        if (authorId <= 0 || destinationId <= 0) {
            throw new IllegalArgumentException(
                    "Author and destination must be positive."
            );
        }
        this.authorId = authorId;
        this.destinationId = destinationId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getDestinationId() {
        return destinationId;
    }
}
