package com.example.mani.notificationapp;

/**
 * Created by mani on 1/26/18.
 */

public class Users extends UserId {
    String name;
    String usertype;

    public Users() {

    }

    public Users(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUsertype() {
        return usertype;
    }
}
