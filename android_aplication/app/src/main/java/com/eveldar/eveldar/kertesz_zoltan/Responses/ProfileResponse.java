package com.eveldar.eveldar.kertesz_zoltan.Responses;

import com.eveldar.eveldar.kertesz_zoltan.Models.User;

public class ProfileResponse {
    User user;

    public ProfileResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
