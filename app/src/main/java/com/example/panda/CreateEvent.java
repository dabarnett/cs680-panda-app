package com.example.panda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/*
   This class uses the create_event layout to accept event data from the users.
   It will then call the add event function in the DB Handler class to add the event to the DB.
 */

public class CreateEvent extends Activity {


    private Context context;

    private String userEventTitle;
    private String userEventAddress;
    private String userEventCity;
    private String userEventState;
    private String userEventDescription;
    private String userEventWebsite;
    private String userContactNumber;
    private String userEventStartTime;
    private String userEventEndTime;
    private String userStarred;


    private DBHandler dbHandler;

    private GoogleApiClient mGoogleApiClient;
    // this will be used to bias, but not limit, the search results in the place API to the US
    private static final LatLngBounds BOUNDS_USA = new LatLngBounds( new LatLng(-125.0011, 24.9493), new LatLng(-66.9326, 49.5904) );


    private EditText txtAddress;
    private EditText txtCity;
    private EditText txtState;


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
        userEventStartTime = ( (EditText) findViewById(R.id.txtStartTime) ).getText().toString();
        userEventEndTime = ( (EditText) findViewById(R.id.txtEndTime) ).getText().toString();
        userEventWebsite = ( (EditText) findViewById(R.id.txtWebsite) ).getText().toString();
        userContactNumber = ( (EditText) findViewById(R.id.contactNumber) ).getText().toString();
        userStarred = "No";
        Event userEvent = new Event(userEventTitle, userEventDescription, userEventStartTime, userEventEndTime,
                                    userEventAddress, userEventCity, userEventState, userEventWebsite, userContactNumber, userStarred);


        // pass event object to the db handler class
        dbHandler = new DBHandler(this);
        boolean success = dbHandler.addEvent(userEvent);

        AlertDialog dialog = new AlertDialog.Builder(CreateEvent.this).create();

        LayoutInflater factory = LayoutInflater.from(CreateEvent.this);
        View dialogView = factory.inflate(R.layout.dialog_image_container, null);
        dialog.setView(dialogView);

        ImageView imgAlert = (ImageView) dialogView.findViewById(R.id.imgAlert);
        imgAlert.setBackgroundResource(R.drawable.alert_animation);

        final AnimationDrawable frameAnimation = (AnimationDrawable) imgAlert.getBackground();



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
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    frameAnimation.start();
                }
            });
            dialog.show();

        }

    }

}
