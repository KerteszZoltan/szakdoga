package com.eveldar.eveldar.kertesz_zoltan;

public class User {
    Integer id;
    String name,email;

    public User(Integer id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
