package com.example.panda;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActivityChooserView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;


// This adapter allows us to customise the event listview
// We can specify the event data to be placed in the listview layout

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private ArrayList<Event> events;
    private int lastPosition = -1;
    private Typeface tf;
    private DBHandler dbHandler;


    public EventAdapter(Context context, int resource, ArrayList<Event> data) {
        super(context, resource, data);
        this.context = context;
        this.events = data;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-SemiBold.ttf");
    }


    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        final Event event = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        // using the event_list_item.xml as a template
        if (row == null)
        {
            row = LayoutInflater.from( getContext()).inflate(R.layout.event_list_item, parent, false);
        }


        lastPosition = position;

        TextView tvEventTtle = (TextView) row.findViewById(R.id.eventTitle);
        tvEventTtle.setText( event.getEventName() );
        tvEventTtle.setTypeface(tf);

        final TextView tvEventStreetAddress = (TextView) row.findViewById(R.id.eventStreetAddress);
        tvEventStreetAddress.setText( event.getStreetAddress() );

        TextView tvEventCityState = (TextView) row.findViewById(R.id.eventCityState);
        tvEventCityState.setText( event.getCity() + ", " + event.getState() );

        ImageView imgEvent = (ImageView) row.findViewById(R.id.bgEvent);
        if( event.getImagePath() == null || event.getImagePath().isEmpty() )
        {
             event.setImagePath( "event_default_img" );
        }
            // the line below will find the id of the image resource with a matching name and then get set the resource
            // with the matching id to the imageview
            int resourceID = context.getResources().getIdentifier( event.getImagePath(), "drawable", context.getPackageName() );
            imgEvent.setImageResource( resourceID );

        Button btnViewLocation = (Button) row.findViewById(R.id.btnShowLocation);
        btnViewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap( event.getStreetAddress(), event.getCity(), event.getState() );
            }
        });

        Button btnViewWebsite = (Button) row.findViewById(R.id.btnWebsite);
        btnViewWebsite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
               openWeb(event.getWebsiteLink());
            }
        });

        Button btnContact = (Button) row.findViewById(R.id.btnContact);

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer(event.getContactNumber());
            }

        });

        ToggleButton toggle = (ToggleButton) row.findViewById(R.id.btnStarred);
        final String starred = event.getStarredStatus();
        if (starred.equals("No")) {
            toggle.setChecked(false);
        }
        else if(starred.equals("Yes")) {
            toggle.setChecked(true);
        }
        else {
            Toast.makeText(context, "There is a problem here! Starred status is " + event.getStarredStatus(), Toast.LENGTH_LONG)
                    .show();
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbHandler = new DBHandler(context);

                if (isChecked) {
                    dbHandler.updateStarredStatus( event.getEventID(), "Yes" );
                    Toast.makeText(context, "The event has been starred!", Toast.LENGTH_LONG)
                            .show();
                } else {
                    // The toggle is disabled
                    Toast.makeText(context, "You've unstarred the event!", Toast.LENGTH_LONG)
                            .show();
                    dbHandler.updateStarredStatus( event.getEventID(), "No" );
                }




            }
        });

        return row;

    }


    //Handles the View Location Button,
    //and sends user to MapsActivity using explicit intent
    public void openMap(String streetAddress, String city, String state){


        Intent mapIntent = new Intent(Intent.ACTION_VIEW,  Uri.parse("geo:0,0?q=" + Uri.encode(streetAddress + ", " + city) ));

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
        }


    }

    //Handles the Event Website Button,
    //and sends the user to WebLookup activity using explicit intent
    public void openWeb(String webAddress){
        Intent intent = new Intent(context, WebLookup.class);
        intent.putExtra("Url", webAddress);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context, "WebLookup does not work!", Toast.LENGTH_LONG)
                    .show();
        }
        else {
            context.startActivity(intent);

        }

    }

    public void openDialer(String contactNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:" + contactNumber));
               context.startActivity(intent);
        }

}
