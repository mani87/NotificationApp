package com.example.mani.notificationapp;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by mani on 1/29/18.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = FirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//
//        String messageTitle = remoteMessage.getNotification().getTitle();
//        String messageBody = remoteMessage.getNotification().getBody();
//
//        NotificationCompat.Builder mBuiler =
//                new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(messageTitle)
//                        .setContentText(messageBody);
//
//        int mNotificationId = (int) System.currentTimeMillis();
//
//        NotificationManager mNotifiMgr =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mNotifiMgr.notify(mNotificationId, mBuiler.build());

    }

}
