package com.shpota.chat.model;

import java.time.LocalDateTime;

public class Message {
    private int messageId;
    private int authorID;
    private int destinationID;
    private LocalDateTime postedDate;
    private String message;

    public Message(int messageId, int authorID, int destinationID, LocalDateTime postedDate, String message) {
        this(authorID, destinationID, postedDate, message);

        if (messageId <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.messageId = messageId;
    }

    public Message(int authorID, int destinationID, LocalDateTime postedDate, String message) {
        if (authorID <= 0 || destinationID <= 0 || postedDate == null || message == null) {
            throw new IllegalArgumentException(
                    "Author, destination, date and message must not be null."
            );
        }
        this.authorID = authorID;
        this.destinationID = destinationID;
        this.postedDate = postedDate;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setAuthorID(int authorID) {
        if (authorID <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.authorID = authorID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setDestinationID(int destinationID) {
        if (destinationID <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.destinationID = destinationID;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        if (postedDate == null) {
            throw new IllegalArgumentException("Date must not be null.");
        }
        this.postedDate = postedDate;
    }

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message must not be null.");
        }
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Message message1 = (Message) o;

        if (messageId != message1.messageId)
            return false;
        if (authorID != message1.authorID)
            return false;
        if (destinationID != message1.destinationID)
            return false;
        if (!postedDate.equals(message1.postedDate))
            return false;
        return message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + authorID;
        result = 31 * result + destinationID;
        result = 31 * result + postedDate.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", authorID=" + authorID +
                ", destinationID=" + destinationID +
                ", postedDate=" + postedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
