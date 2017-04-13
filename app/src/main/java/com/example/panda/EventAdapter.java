package com.example.panda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


// This adapter allows us to customise the event listview
// We can specify the event data to be placed in the listview layout

public class EventAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> events;
    private int lastPosition = -1;


    public EventAdapter(Context context, int resource, ArrayList<Event> data) {
        super(context, resource, data);
        this.events = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        // using the event_list_item.xml as a template
        if (convertView == null)
        {
            convertView = LayoutInflater.from( getContext()).inflate(R.layout.event_list_item, parent, false);
        }

        lastPosition = position;
        TextView tvEventTtle = (TextView) convertView.findViewById(R.id.eventTitle);
        tvEventTtle.setText( event.getEventName() );

        TextView tvEventAddress = (TextView) convertView.findViewById(R.id.eventAddress);
        tvEventAddress.setText( event.getAddress() );

        return convertView;

    }

}
