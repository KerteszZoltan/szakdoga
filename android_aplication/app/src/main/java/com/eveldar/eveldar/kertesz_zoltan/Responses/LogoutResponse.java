package com.eveldar.eveldar.kertesz_zoltan.Responses;

public class LogoutResponse {
    String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
