package com.example.mani.notificationapp.dataModels;

import com.example.mani.notificationapp.main.UserId;

/**
 * Created by mani on 1/26/18.
 */

public class Users extends UserId {
    private String name;
    private String usertype;

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
