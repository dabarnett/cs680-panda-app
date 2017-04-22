package com.example.panda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

        LayoutInflater factory = LayoutInflater.from(CreateEvent.this);
        final View dialogView = factory.inflate(R.layout.dialog_image_container, null);
        dialog.setView(dialogView);

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

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",	new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Go back to event list
                    Intent intentEventList = new Intent(CreateEvent.this, MainActivity.class);
                    startActivity(intentEventList);
                }
            });

            ImageView imgAlert = (ImageView) findViewById(R.id.imgAlert);
            imgAlert.setBackgroundResource(R.drawable.alert_animation);

            AnimationDrawable frameAnimation = (AnimationDrawable) imgAlert.getBackground();
            frameAnimation.start();

            dialog.show();

        }

    }

}
