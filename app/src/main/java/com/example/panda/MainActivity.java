package com.example.panda;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DBHandler dbHandler;
    private SQLiteDatabase db;
    private ArrayList<Event> eventList;
    private EventAdapter adapter;
    private ListView eventListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TabHost host = (TabHost) findViewById(R.id.tabhost);
        host.setup();

        //Events Tab
        TabHost.TabSpec spec = host.newTabSpec("tabCurrentEvents");
        spec.setContent(R.id.tabCurrentEvents);
        spec.setIndicator("Current Events");
        host.addTab(spec);


        dbHandler = new DBHandler(this);


        //Create the events database
        try
        {
            db = dbHandler.getWritableDatabase();
        }
        catch(SQLException e)
        {
            Log.d("DB LOG: ", "Create database failed");
        }


        try
        {

            // if the database is emoty then populate it with demo events
            if( dbHandler.getRecordCount() == 0 )
            {
                dbHandler.initialiseDatabase();
                dbHandler.getAllEvents();
            }

                Log.d("DEBUG:", "NOT NEEDED");
                eventList = dbHandler.getAllEvents();

                eventListView = (ListView)findViewById(R.id.eventListView);
                adapter = new EventAdapter(this, R.layout.event_list_item, eventList);
                eventListView.setAdapter(adapter);

        }
        catch (Exception e)
        {
            Log.d("ERROR LOG: ", e.getMessage() );
        }

    }


    public void loadEventsList()
    {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
