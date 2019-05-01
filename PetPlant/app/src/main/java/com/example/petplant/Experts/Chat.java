package com.example.petplant.Experts;

public class Chat {

    private String sender;
    private String receiver;
    private String message;
    private Integer image;

    public Chat(String sender, String receiver, String message, Integer image) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.image = image;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
