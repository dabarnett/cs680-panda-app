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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private DBHandler dbHandler;
    private SQLiteDatabase db;
    //private SQLiteDatabase db2;
    private ArrayList<Event> eventList;
    private ArrayList<Item> itemList;
    private EventAdapter adapter;
    private ItemAdapter itemadapter;
    private ListView eventListView;
    private ListView itemListView;



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
            //db2 = dbHandler.getWritableDatabase();
        }
        catch(SQLException e)
        {
            Log.d("DB LOG: ", "Create database failed");
        }


        try
        {

            // if the database is empty then populate it with demo events
            if( dbHandler.getRecordCount() == 0 )
            {
                dbHandler.initialiseDatabase();
                dbHandler.getAllEvents();
                dbHandler.getAllItems();
            }

                Log.d("DEBUG:", "NOT NEEDED");
                eventList = dbHandler.getAllEvents();
                itemList = dbHandler.getAllItems();

                eventListView = (ListView)findViewById(R.id.eventListView);
                itemListView = (ListView)findViewById(R.id.itemListView);
                adapter = new EventAdapter(this, R.layout.event_list_item, eventList);
                itemadapter = new ItemAdapter(this, R.layout.sell_item, itemList);

                eventListView.setAdapter(adapter);
                itemListView.setAdapter(itemadapter);

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
        switch (id)
        {
            case R.id.action_createEvent:

                Log.d("MENU ACTION", "CREATE EVENT SELECTED");
                    Intent menuIntent = new Intent(this, CreateEvent.class);
                    startActivity(menuIntent);
            return true;

            case R.id.action_sellItem:

                Log.d("MENU ACTION", "SELL ITEM SELECTED");
                menuIntent = new Intent(this, SellItem.class);
                startActivity(menuIntent);

            default: super.onOptionsItemSelected(item);


        }

        return false;
    }

}



