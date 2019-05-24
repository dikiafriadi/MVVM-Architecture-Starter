package com.aditp.mdvkarch.helper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.aditp.mdvkarch.BuildConfig;
import com.aditp.mdvkarch.R;


/**
 * @AUTHOR : A D I T Y A   P R A T A M A
 * @DATE : 06/04/2017
 */


public class LocalNotification {

    private static NotificationManager notifManager;

    public static void createNotification(String aTitle, String aMessage, Context context) {
        final int NOTIFY_ID = 0; // ID of notification
        String id = BuildConfig.VERSION_NAME; // default_channel_id
        String title = context.getString(R.string.app_name); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        // GETTING SERVICE
        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        // SINCE ANDROID 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);


            // OPEN URL
            //String uri1 = getIt("be28b91e90e37425bb20a81dc31977dccd1dab17841da9ddb729b916c419bcddc223ac1dcb28741ac51bb423ca2c731ebe24");
            //intent = new Intent(context, CustomWeb.class);
            //intent.putExtra("URL", uri1);
            //pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);

            builder.setContentTitle(aTitle)                             // required
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.BADGE_ICON_LARGE)
                    .setAutoCancel(true)
                    // .setContentIntent(pendingIntent) -> open intent
                    .setTicker(aTitle)
                    //.setOngoing(true)
                    .setLights(Color.BLUE, 3000, 3000)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        }

        // OTHER SDK
        else {
            builder = new NotificationCompat.Builder(context, id);

            //String uri1 = getIt("be28b91e90e37425bb20a81dc31977dccd1dab17841da9ddb729b916c419bcddc223ac1dcb28741ac51bb423ca2c731ebe24");
            //intent = new Intent(context, CustomWeb.class);
            //intent.putExtra("URL", uri1);
            //pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

            builder.setContentTitle(aTitle)                            // required
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)   // required
                    .setContentText(aMessage) // required
                    .setAutoCancel(true)
                    .setTicker(aTitle)
                    //.setOngoing(true)
                    .setLights(Color.BLUE, 3000, 3000)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
