package com.example.panda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

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

        //Events Tab
        spec = host.newTabSpec("tabMarketplace");
        spec.setContent(R.id.tabMarketplace);
        spec.setIndicator("Marketplace");
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

    //Handles the Event Website Button,
    //and sends the user to WebLookup activity using explicit intent
    public void openWeb(View view){
        Intent intent = new Intent(this, WebLookup.class);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "WebLookup does not work!", Toast.LENGTH_LONG)
                    .show();
        }
        else {
            startActivity(intent);

        }

    }

}
