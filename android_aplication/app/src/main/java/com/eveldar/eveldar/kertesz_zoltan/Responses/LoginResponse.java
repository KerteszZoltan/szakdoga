package com.eveldar.eveldar.kertesz_zoltan.Responses;

import com.eveldar.eveldar.kertesz_zoltan.Models.User;

public class LoginResponse {
    User user;
    String token;

    public LoginResponse(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
