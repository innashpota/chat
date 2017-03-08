package com.shpota.chat.model;

import java.time.OffsetDateTime;

public class Message {
    private int messageId;
    private int authorId;
    private int destinationId;
    private OffsetDateTime postedDate;
    private String message;

    public Message(int messageId, int authorId, int destinationId, OffsetDateTime postedDate, String message) {
        this(authorId, destinationId, postedDate, message);

        if (messageId <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.messageId = messageId;
    }

    public Message(int authorId, int destinationId, OffsetDateTime postedDate, String message) {
        if (authorId <= 0 || destinationId <= 0 || postedDate == null || message == null) {
            throw new IllegalArgumentException(
                    "Author, destination, date and message must not be null."
            );
        }
        this.authorId = authorId;
        this.destinationId = destinationId;
        this.postedDate = postedDate;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setAuthorId(int authorId) {
        if (authorId <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setDestinationId(int destinationId) {
        if (destinationId <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.destinationId = destinationId;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setPostedDate(OffsetDateTime postedDate) {
        if (postedDate == null) {
            throw new IllegalArgumentException("Date must not be null.");
        }
        this.postedDate = postedDate;
    }

    public OffsetDateTime getPostedDate() {
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
        if (authorId != message1.authorId)
            return false;
        if (destinationId != message1.destinationId)
            return false;
        if (!postedDate.equals(message1.postedDate))
            return false;
        return message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + authorId;
        result = 31 * result + destinationId;
        result = 31 * result + postedDate.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", authorId=" + authorId +
                ", destinationId=" + destinationId +
                ", postedDate=" + postedDate +
                ", message='" + message + '\'' +
                '}';
    }
}
