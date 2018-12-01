package com.example.v.newsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {

    private static final int NEWS_UPDATE_NOTIFICATION_ID = 222;
    private static final int NEWS_UPDATE_PENDING_INTENT_ID = 111;
    private static final String NEWS_UPDATE_NOTIFICATION_CHANNEL_ID = "update_notfication_channel";
    private static final int ACTION_IGNORE_PENDING_INTENT = 1111;

    public static void notifyNews(Context context){
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    NEWS_UPDATE_NOTIFICATION_CHANNEL_ID, context.getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NEWS_UPDATE_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_icons8_google_news_50)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_content)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(ignoreUpdateAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(NEWS_UPDATE_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, NEWS_UPDATE_PENDING_INTENT_ID,
                startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }

    public static NotificationCompat.Action ignoreUpdateAction(Context context){
        Intent ignoreUpdateIntent = new Intent(context, NewsUpdateIntentService.class);
        ignoreUpdateIntent.setAction(UpdateTask.ACTION_DISMISS_NOTIFICATION);

        PendingIntent ignoreReminderPendingIntent = PendingIntent
                .getService(context, ACTION_IGNORE_PENDING_INTENT, ignoreUpdateIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.ic_highlight_off_black_24dp,
                "Cancel",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;

    }

    private static Bitmap largeIcon(Context context){
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_google_news);

        return largeIcon;
    }
}



