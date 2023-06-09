package com.mz.befclient.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mz.befclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class FCMMessagingService extends FirebaseMessagingService {
    String type = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        super.onMessageReceived(remoteMessage);
    }

    private void sendNotification(String title2,String messageBody) {
        //Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "heads up notification", importance);
            getSystemService(NotificationManager.class).createNotificationChannel(mChannel);
            Notification.Builder notification = new Notification.Builder(this,CHANNEL_ID);
            notification.setContentTitle(title2);
            notification.setContentText(messageBody);
            notification.setSmallIcon(R.drawable.logo);
            notification.setAutoCancel(true);
            NotificationManagerCompat.from(this).notify(1,notification.build());
        }
        /*Intent intent = new Intent("com.alatheer.noamany_FCM-MESSAGE");
        intent.putExtra("title",title2);
        intent.putExtra("message",messageBody);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);*/
    }
}