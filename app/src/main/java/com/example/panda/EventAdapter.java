package com.example.panda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


// This adapter allows us to customise the event listview
// We can specify the event data to be placed in the listview layout

public class EventAdapter extends ArrayAdapter<Event> {

    private Context context;
    private ArrayList<Event> events;
    private int lastPosition = -1;
    private Typeface tf;




    public EventAdapter(Context context, int resource, ArrayList<Event> data) {
        super(context, resource, data);
        this.context = context;
        this.events = data;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-SemiBold.ttf");
    }


    @Override
    public View getView(int position, View row, ViewGroup parent) {

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
        imgEvent.setImageResource(R.drawable.event_harborcruise);


        Button btnViewLocation = (Button) row.findViewById(R.id.btnShowLocation);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap( event.getStreetAddress(), event.getCity(), event.getState() );
            }
        });



        return row;

    }


    //Handles the View Location Button,
    //and sends user to MapsActivity using explicit intent
    public void openMap(String streetAddress, String city, String state){
        
        Intent mapIntent = new Intent(context, MapsActivity.class);
        mapIntent.putExtra("streetAddress", streetAddress);
        mapIntent.putExtra("city", city);
        mapIntent.putExtra("state", state);
        context.startActivity(mapIntent);
    }



}
