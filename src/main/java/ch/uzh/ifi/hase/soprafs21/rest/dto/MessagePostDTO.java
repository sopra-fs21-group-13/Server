package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.time.LocalDateTime;

public class MessagePostDTO {

    private LocalDateTime timeStamp;
    private Long senderId;
    private String message;


    // Setters & Getters

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

