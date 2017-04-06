package com.example.panda;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DBHandler dbHandler;
    private SQLiteDatabase db;
    private ArrayList<Event> eventList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        dbHandler = new DBHandler(this);

        //create database
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
            else
            {
                Log.d("DEBUG:", "NOT NEEDED");
                dbHandler.getAllEvents();
            }
        }
        catch (Exception e)
        {
            Log.d("ERROR LOG: ", e.getMessage() );
        }

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
