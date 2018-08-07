package com.example.meirai.notificationalarmapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notificationId";
    public static String NOTIFICATION_CONTENT = "content";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onreceive", "onreceive");
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID,0);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String content = intent.getStringExtra(NOTIFICATION_CONTENT);
        String title = context.getString(R.string.app_name);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(
                    content, title, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableVibration(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(channel);
        }


        notificationManager.notify(id,buildNotification(context, content));
    }

    private Notification buildNotification(Context context, String content) {
        Log.d("build", "build");

        if (Build.VERSION.SDK_INT >= 26) {
            Notification.Builder builder = new Notification.Builder(context, content);
            builder.setContentTitle("Notification!!")
                    .setContentText(content)
                    .setChannelId(content)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon);
            return builder.build();
        }else{
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("Notification!!")
                    .setContentText(content)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon);
            if (Build.VERSION.SDK_INT < 16) {
                return builder.getNotification();
            } else {
                return builder.build();
            }
        }


    }
}
