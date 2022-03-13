package com.eveldar.eveldar.kertesz_zoltan;

public class User {
    String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    Integer id;
    String name,email;

    public User(Integer id,String token, String email, String name) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
