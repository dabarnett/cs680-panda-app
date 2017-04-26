package com.example.panda;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private Intent notifyIntent;

    String notificationMessage;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        this.notifyIntent = intent;

        //collects the thread name and magic number from the main activity
        //and composes the notification and log message
        String eventName = intent.getStringExtra("eventName");
        notificationMessage = "A new event named " + eventName + " has been created";

        //writes the number and thread to the log with the tag Magic
        Log.i("Magic", notificationMessage);

        //shows the notification composed
        showNotification(context);
    }
    //Sets and composes the notification
    public void showNotification(Context context){

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent,
                                        PendingIntent.FLAG_ONE_SHOT);

        Notification magicNotification = new Notification.Builder(context)
                .setContentTitle("New Event Created")    //set Notification text and icon
                .setContentText(notificationMessage)
                .setSmallIcon(R.drawable.panda)
                .setTicker("New event created")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setVibrate(new long[] {1000, 1000, 1000, 1000})
                .setLights(Integer.MAX_VALUE,  500,  500)
                .setAutoCancel(true)               // auto close notification on click
                .build();

        NotificationManager notificationManager = (NotificationManager)
                                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, magicNotification);
    }
}
