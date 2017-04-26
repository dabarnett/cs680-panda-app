package com.example.panda;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private DBHandler dbHandler;
    private SQLiteDatabase db;
    //private SQLiteDatabase db2;
    private ArrayList<Event> eventList;
    private ArrayList<Item> itemList;
    private EventAdapter adapter;
    private ItemAdapter itemadapter;
    private ShowStarred showStarred;
    private ListView eventListView;
    private ListView itemListView;

    private TextToSpeech speaker;
    private String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Text to Speech engine (context, listener object)
        speaker = new TextToSpeech(this, this);
        speaker.setLanguage(Locale.US);

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
        catch (SQLException e)
        {
            Log.d("ERROR LOG: ", e.getMessage() );
        }

    }

    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // If a language is not be available, the result will indicate it.
            int result = speaker.setLanguage(Locale.US);

            //  int result = speaker.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language data is missing or the language is not supported.",
                                Toast.LENGTH_LONG)
                        .show();
            }
        }
        else {
            Toast.makeText(this, "Initialization failed!", Toast.LENGTH_LONG)
                    .show();
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
                    speaker.speak("You are now creating a new event", TextToSpeech.QUEUE_FLUSH, null, "New");
                return true;

            case R.id.action_showStarred:

                Log.d("MENU ACTION", "SHOW STARRED EVENTS SELECTED");
                showStarred = new ShowStarred(this, R.layout.starred_list_item, eventList);
                speaker.speak("You are now showing starred events", TextToSpeech.QUEUE_FLUSH, null, "New");
                return true;

            case R.id.action_sellItem:

                Log.d("MENU ACTION", "SELL ITEM SELECTED");
                menuIntent = new Intent(this, SellItem.class);
                startActivity(menuIntent);
                speaker.speak("You are now selling an item", TextToSpeech.QUEUE_FLUSH, null, "New");
                return true;

            default: super.onOptionsItemSelected(item);


        }

        return false;
    }

}



