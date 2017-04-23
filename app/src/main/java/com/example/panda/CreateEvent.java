package com.example.panda;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;


/*
   This class uses the create_event layout to accept event data from the users.
   It will then call the add event function in the DB Handler class to add the event to the DB.
 */

public class CreateEvent extends Activity {


    private String userEventTitle;
    private String userEventAddress;
    private String userEventCity;
    private String userEventState;
    private String userEventDescription;
    private String userEventWebsite;
    private Intent notifyIntent;
    private String message;

    private Context context;


    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        Button btnSaveEvent = (Button) findViewById(R.id.btnSaveEvent);
        btnSaveEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createEvent(view);
            }
        });

        this.context = this;
        notifyIntent = new Intent(this, MainActivity.class);

    }

    public void createEvent(View view){

        // get the event details entered an entered into an event object
        userEventTitle = ( (EditText) findViewById(R.id.txtEventName) ).getText().toString();
        userEventAddress = ( (EditText) findViewById(R.id.txtAddress) ).getText().toString();
        userEventCity = ( (EditText) findViewById(R.id.txtEventCity) ).getText().toString();
        userEventState = ( (EditText) findViewById(R.id.txtEventState) ).getText().toString();
        userEventDescription = ( (EditText) findViewById(R.id.txtDescription) ).getText().toString();
        userEventWebsite = ( (EditText) findViewById(R.id.txtWebsite) ).getText().toString();
        Event userEvent = new Event(userEventTitle, userEventDescription, userEventAddress, userEventCity, userEventState, userEventWebsite);


        // pass event object to the db handler class
        dbHandler = new DBHandler(this);
        boolean success = dbHandler.addEvent(userEvent);

        AlertDialog dialog = new AlertDialog.Builder(CreateEvent.this).create();

        if(!success)
        {
            // this will show up if there was an error adding the event to the database
            dialog.setTitle("Sorry");
            dialog.setMessage("There was an error creating your event.");

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",	new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Go back to event list
                    Intent intentEventList = new Intent(CreateEvent.this, MainActivity.class);
                    startActivity(intentEventList);
                }
            });

            dialog.show();
        }
        else
        {
            dialog.setTitle("Congrats!");
            dialog.setMessage("Your event was created");

            message = "A new event named " + userEventTitle + " have been created.";

            showNotification(context);

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",	new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Go back to event list
                    Intent intentEventList = new Intent(CreateEvent.this, MainActivity.class);
                    startActivity(intentEventList);
                }
            });

            dialog.show();

        }

    }
    //Sets and composes the notification
    public void showNotification(Context context){

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification magicNotification = new Notification.Builder(context)
                .setContentTitle("New event created")    //set Notification text and icon
                .setContentText(message)
                .setSmallIcon(R.drawable.panda)
                .setTicker("New event created!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setVibrate(new long[] {1000, 1000, 1000, 1000})
                .setLights(Integer.MAX_VALUE,  500,  500)
                .setAutoCancel(true)                                // auto close notification on click
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, magicNotification);
    }

}
