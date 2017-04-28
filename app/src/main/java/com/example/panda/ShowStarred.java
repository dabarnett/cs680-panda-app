package com.example.panda;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;


public class ShowStarred extends Activity {

    private Context context;
    private DBHandler dbHandler;
    private SQLiteDatabase db;
    private ArrayList<Event> starredEventList;
    private EventAdapter adapter;
    private ListView eventListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starred_events_list);

        this.context = this;
        Intent intent = getIntent();

        dbHandler = new DBHandler(this);

        try
        {
            starredEventList = dbHandler.getStarredEvents();
        }
        catch (SQLException e)
        {
            Log.d("ERROR LOG: ", e.getMessage() );
        }


        eventListView = (ListView) findViewById(R.id.eventListView);
        adapter = new EventAdapter(this, R.layout.event_list_item, starredEventList);
        eventListView.setAdapter(adapter);

    }




}