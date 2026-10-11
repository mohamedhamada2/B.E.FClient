package com.mz.befclient.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mz.befclient.R;
import com.mz.befclient.productDetails.ProductDetailsActivity;
import com.mz.befclient.splash.SplashActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class FCMMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCMMessagingService";
    private static final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
    // Data keys sent by the backend: "message" => {title, body}, "moredata" => {id}
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_MORE_DATA = "moredata";
    public static final String KEY_PRODUCT_ID = "id";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        Log.e("message", data.toString());
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        String title = null, body = null;
        if (notification != null) {
            title = notification.getTitle();
            body = notification.getBody();
        }
        // Data-only message (or missing notification fields): read them from data.message
        JSONObject message = parseJson(data.get(KEY_MESSAGE));
        if (message != null) {
            if (isEmpty(title)) {
                title = message.optString("title", getString(R.string.app_name));
            }
            if (isEmpty(body)) {
                body = message.optString("body");
            }
        }
        // The screen to open depends only on the id inside moredata
        String productId = extractProductId(data.get(KEY_MORE_DATA));
        sendNotification(title, body, productId);
    }

    /**
     * Reads the value of the "id" key out of the backend "moredata" value,
     * e.g. {"id":"15"}, {"id":15} or {id=15}. Returns null when there is no id.
     */
    public static String extractProductId(String moredata) {
        if (isEmpty(moredata)) {
            return null;
        }
        JSONObject json = parseJson(moredata);
        if (json != null) {
            if (json.isNull(KEY_PRODUCT_ID)) {
                return null;
            }
            String id = json.optString(KEY_PRODUCT_ID, null);
            return isEmpty(id) ? null : id.trim();
        }
        // Not json: fall back to matching id=15 / "id":15
        Matcher matcher = Pattern.compile("\"?" + KEY_PRODUCT_ID + "\"?\\s*[:=]\\s*\"?([^\",}\\s]+)").matcher(moredata);
        return matcher.find() ? matcher.group(1) : null;
    }

    private void sendNotification(String title, String message, String productId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "heads up notification", importance);
            getSystemService(NotificationManager.class).createNotificationChannel(mChannel);
        }
        Intent intent;
        if (isEmpty(productId)) {
            // No product id: open the whole app from the start
            intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            // Tapping the notification opens the product details screen, which calls get_product_details with this id
            intent = ProductDetailsActivity.newIntent(this, productId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        int requestCode = isEmpty(productId) ? 0 : productId.hashCode();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setSmallIcon(R.drawable.logo);
        notification.setPriority(NotificationCompat.PRIORITY_HIGH);
        notification.setAutoCancel(true);
        notification.setContentIntent(PendingIntent.getActivity(this, requestCode, intent, flags));
        // No-op (and logged by the platform) when POST_NOTIFICATIONS is denied on Android 13+.
        NotificationManagerCompat.from(this).notify(1, notification.build());
    }

    private static JSONObject parseJson(String text) {
        if (text == null || !text.trim().startsWith("{")) {
            return null;
        }
        try {
            return new JSONObject(text);
        } catch (JSONException e) {
            Log.w(TAG, "Not valid json: " + text, e);
            return null;
        }
    }

    private static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
