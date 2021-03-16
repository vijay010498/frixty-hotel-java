package com.frixty.frixtyhotel.Models;

public class MessageBodyResponse {
    private String message;

    public MessageBodyResponse(String message) {
        this.message = message;
    }

    public MessageBodyResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
