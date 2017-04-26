package com.example.panda;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
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
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/*
   This class uses the create_event layout to accept event data from the users.
   It will then call the add event function in the DB Handler class to add the event to the DB.
 */

public class CreateEvent extends Activity implements PlaceSelectionListener {


    private Context context;

    private String userEventTitle;
    private String userEventAddress;
    private String userEventCity;
    private String userEventState;
    private String userEventDescription;
    private String userEventWebsite;
    private String userContactNumber;
    private String userEventDate;
    private String userEventStartTime;
    private String userEventEndTime;
    private String userStarred;


    private DBHandler dbHandler;

    private GoogleApiClient mGoogleApiClient;
    // this will be used to bias, but not limit, the search results in the place API to the US
    private static final LatLngBounds BOUNDS_USA = new LatLngBounds( new LatLng(-125.0011, 24.9493),
                                                                        new LatLng(-66.9326, 49.5904) );


    private EditText txtAddress;
    private EditText txtCity;
    private EditText txtState;
    private EditText txtDate;
    private EditText txtStartTime;
    private EditText txtEndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);


        this.context = this;

        Button btnSaveEvent = (Button) findViewById(R.id.btnSaveEvent);
        btnSaveEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                createEvent(view);
            }
        });





        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtCity = (EditText) findViewById(R.id.txtEventCity);
        txtState = (EditText) findViewById(R.id.txtEventState);

        // this sets up the Google Place API to get address suggestions
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .build();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        autocompleteFragment.setHint("Pick an event location");


        // this section will call the timepicker when the starttime and endtime editexts are selected.
        // the ontimesetlistener will determine how to handle the
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        final TimePickerDialog.OnTimeSetListener startTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                  // change 24 hour time to 12 hour time format with am/pm
                  boolean isPM = (hourOfDay >= 12);
                  txtStartTime.setText( String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM") );
                }
            };

        txtStartTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    try
                    {
                        DialogFragment timePickerFragment = new TimePickerFragment(startTimeSetListener);
                        timePickerFragment.show(getFragmentManager(), "TimePicker");
                    }
                    catch (Exception e)
                    {
                        Log.d("TIMEPICKER DEBUG", e.getMessage());
                    }
                }

            }
        });

        final TimePickerDialog.OnTimeSetListener endTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
                        boolean isPM = (hourOfDay >= 12);
                        txtEndTime.setText( String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM") );
                    }
                };


        // does same as aboe but for end time
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        txtEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    try
                    {
                        DialogFragment timePickerFragment = new TimePickerFragment(endTimeSetListener);
                        timePickerFragment.show(getFragmentManager(), "TimePicker");
                    }
                    catch (Exception e)
                    {
                        Log.d("TIMEPICKER DEBUG", e.getMessage());
                    }
                }

            }
        });
        // end of timepicker code for event start and end date


        //this section is similar to the time section except that it calls a date picker
        // when the start date is chosen
        txtDate = (EditText) findViewById(R.id.txtDate);
        final DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int month, int day) {
                    txtDate.setText( String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year) );
                }
            };
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    try
                    {
                        DialogFragment datePickerFragment = new DatePickerFragment(startDateSetListener);
                        datePickerFragment.show(getFragmentManager(), "DatePicker");
                    }
                    catch (Exception e)
                    {
                        Log.d("DATEPICKER DEBUG", e.getMessage());
                    }
                }

            }
        });





    }

    // this method is called when a suggestion from the placeautocomplete fragment
    // supplied by the API is selected.
    @Override
    public void onPlaceSelected(Place place) {
        Log.i("PLACE LISTENER DEBUG", "Place Selected: " + place.getName());
        txtAddress.setText( place.getName() + ", " + place.getAddress() );
        txtCity.setEnabled(false);
        txtState.setEnabled(false);

    }

    @Override
    public void onError(Status status) {
        Log.e("PLACE LISTENER DEBUG", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();

    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    public void createEvent(View view){

        // get the event details entered an entered into an event object
        userEventTitle = ( (EditText) findViewById(R.id.txtEventName) ).getText().toString();
        userEventAddress = ( (EditText) findViewById(R.id.txtAddress) ).getText().toString();
        userEventCity = ( (EditText) findViewById(R.id.txtEventCity) ).getText().toString();
        userEventState = ( (EditText) findViewById(R.id.txtEventState) ).getText().toString();
        userEventDescription = ( (EditText) findViewById(R.id.txtDescription) ).getText().toString();
        userEventDate = ( (EditText) findViewById(R.id.txtDate) ).getText().toString();
        userEventStartTime = ( (EditText) findViewById(R.id.txtStartTime) ).getText().toString();
        userEventEndTime = ( (EditText) findViewById(R.id.txtEndTime) ).getText().toString();
        userEventWebsite = ( (EditText) findViewById(R.id.txtWebsite) ).getText().toString();
        userContactNumber = ( (EditText) findViewById(R.id.contactNumber) ).getText().toString();
        userStarred = "No";
        Event userEvent = new Event(userEventTitle, userEventDescription, userEventDate, userEventStartTime, userEventEndTime,
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

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",
                                new DialogInterface.OnClickListener() {
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

            //connects to the broadcast receiver and sends the correct data to it
            Intent notifyIntent = new Intent();
            notifyIntent.putExtra("eventName", userEventTitle);
            notifyIntent.setAction("com.example.panda.CUSTOM_INTENT");
            sendBroadcast(notifyIntent);

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",
                                new DialogInterface.OnClickListener() {
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
