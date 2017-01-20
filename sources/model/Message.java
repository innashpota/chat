package model;

import java.time.LocalDateTime;

public class Message {
    private int authorID;
    private int destinationID;
    private LocalDateTime postedDate;
    private String message;

    public Message(int authorID, int destinationID, LocalDateTime postedDate, String message) {
        if (authorID == 0 || destinationID == 0 || postedDate == null || message == null) {
            throw new IllegalArgumentException(
                    "Author, destination, date and message can not be null."
            );
        }
        this.authorID = authorID;
        this.destinationID = destinationID;
        this.postedDate = postedDate;
        this.message = message;
    }

    public void setAuthorID(int authorID) {
        if (authorID == 0) {
            throw new IllegalArgumentException("Author can not be null.");
        }
        this.authorID = authorID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setDestinationID(int destinationID) {
        if (destinationID == 0) {
            throw new IllegalArgumentException("Destination can not be null.");
        }
        this.destinationID = destinationID;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        if (postedDate == null) {
            throw new IllegalArgumentException("Date can not be null.");
        }
        this.postedDate = postedDate;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message can not be null.");
        }
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
