package com.example.mani.notificationapp.main;

import android.support.annotation.NonNull;

/**
 * Created by mani on 1/27/18.
 */

public class UserId {
    public String userId;

    public <T extends UserId> T withId(@NonNull final String id) {
        this.userId = id;
        return (T) this;
    }
}
