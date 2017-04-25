package com.example.panda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class DBHandler extends SQLiteOpenHelper {
    // Database properties
    private static final String DATABASE_NAME = "pandaDB";
    private static final String TABLE_NAME = "Events";
    private static final String ITEM_TABLE_NAME = "Items";
    private static final int DATABASE_VERSION = 3;

    private Cursor cursor;
    private ContentValues values;
    private ContentValues itemvalues;


    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESC = "description";
    public static final String KEY_ADDRESS = "street_address";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_LINK = "website_link";
    public static final String KEY_NUMBER = "contact_number";
    public static final String KEY_START = "start_time";
    public static final String KEY_END = "end_time";
    public static final String KEY_ITEM_ID = "id";
    public static final String KEY_ITEM_NAME = "name";
    public static final String KEY_ITEM_PRICE = "price";
    public static final String KEY_ITEM_DESC = "description";
    public static final String KEY_ITEM_CITY = "city";
    public static final String KEY_ITEM_STATE = "state";
    public static final String KEY_ITEM_NUMBER = "contact_number";


    public static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                                + KEY_ID + " INTEGER primary key autoincrement,"
                                                + KEY_NAME + " TEXT, "
                                                + KEY_DESC + " TEXT, "
                                                + KEY_START + " TEXT, "
                                                + KEY_END + " TEXT, "
                                                + KEY_ADDRESS + " TEXT, "
                                                + KEY_CITY + " TEXT, "
                                                + KEY_STATE + " TEXT, "
                                                + KEY_LINK + " TEXT, "
                                                + KEY_NUMBER + " TEXT)" ;

    public static final String ITEM_QUERY_CREATE_TABLE = "CREATE TABLE " + ITEM_TABLE_NAME + " ("
            + KEY_ITEM_ID + " INTEGER primary key autoincrement,"
            + KEY_ITEM_NAME + " TEXT, "
            + KEY_ITEM_PRICE + " TEXT, "
            + KEY_ITEM_DESC + " TEXT, "
            + KEY_ITEM_CITY + " TEXT, "
            + KEY_ITEM_STATE + " TEXT, "
            + KEY_ITEM_NUMBER + " TEXT)" ;


    private ArrayList<Event> Events;
    private ArrayList<Item> Items;



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
        db.execSQL(ITEM_QUERY_CREATE_TABLE);;
    }


    //if older db version exists, drop it and replace
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        Log.d("SQLiteDemo", "onUpgrade: Version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
        onCreate(db);
    }



    /*
    *    This function is for demo purposes
    *    It populates the database with some events to get the user started
    */
    public void initialiseDatabase() {

        // create some new events using the custom event class (Event.java)
        // One of the constructors accepts the name, description, address (street address, city, state) and website link of an event
        addEvent( new Event( "Annual GSA Boston Harbor Cruise",
                                "Details and tickets to follow soon.",
                                "1PM",
                                "10PM",
                                "60 Rowes Wharf",
                                "Boston",
                                "MA",
                                "https://www.facebook.com/events/238965596572122/",
                                "781-555-2113") );
        addEvent( new Event( "Red Sox vs Tampa Bay Rays",
                                "Come watch a Red Sox Game at the Red Sox Stadium Fenway Park Boston with GSA and start your weekend on a fun note. Tickets are Available on My Bentley",
                                "6PM",
                                "9PM",
                                "Fenway Park 4 Yawkey Way",
                                "Boston",
                                "MA",
                                "https://www.facebook.com/events/271118446666257/",
                                "781-555-2116") );
        addEvent( new Event( "Celebrating Harry Bentley's Birthday",
                                "A time capsule from Bentley’s 75th anniversary will be on display in the library all day, and students, faculty, staff, alumni, and all other members of our community can use this as inspiration for contributing their own items into Bentley’s Centennial time capsule. We will begin celebrating Harry Bentley’s birthday in the Pub, where there will be cake, food, and the reading of a letter written at Bentley's 75th anniversary.",
                                "3PM",
                                "8PM",
                                "Bentley University, 175 Forest Street",
                                "Waltham",
                                "MA",
                                "https://www.facebook.com/events/1016727698433556/",
                                "781-555-2115") );
        addEvent( new Event( "The Week of World Food",
                                "Please stop by in the smith lobby next week from March 27th to 30th to enjoy GSA's yearly diversity event. Each day we have food from a different region in the world.",
                                "2PM",
                                "5PM",
                                "Bentley University, 175 Forest St",
                                "Waltham",
                                "MA",
                                "https://www.facebook.com/events/2079322972294483/",
                                "781-555-2114") );
        addItem( new Item( "Tape Drive",
                "LTO 4 Tape Drive.",
                "$400",
                "Boston",
                "MA",
                "781-555-2113") );

        addItem( new Item( "Proliant G4 Server",
                "HP Proliant G4 Server",
                "$400",
                "Wilmington",
                "MA",
                "781-555-2115") );

        addItem( new Item( "LTO 4 Tapes",
                "LTO 4 Tapes",
                "$100",
                "Waltham",
                "MA",
                "781-555-2119") );

        addItem( new Item( "Rockstar Punched",
                "Best Energy Drink Ever",
                "$4",
                "Stoneham",
                "MA",
                "781-555-2112") );
    }


    /*
    *    addEvent method accepts an event class instance and
    *    inserts it into the database
    */
    public boolean addEvent(Event event){
        boolean success = false;

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();

            values = new ContentValues();
            //values.put(KEY_ID, event.getEventID() );
            values.put(KEY_NAME, event.getEventName() );
            values.put(KEY_DESC, event.getEventDescription() );
            values.put(KEY_START, event.getStartDateTime() );
            values.put(KEY_END, event.getEndDateTime() );
            values.put(KEY_ADDRESS, event.getStreetAddress() );
            values.put(KEY_CITY, event.getCity() );
            values.put(KEY_STATE, event.getState() );
            values.put(KEY_LINK, event.getWebsiteLink() );
            values.put(KEY_NUMBER, event.getContactNumber() );

            // insert function returns -1 if an error occurred OR ID no. of inserted record on success
           if( db.insert(TABLE_NAME, null, values) != -1 )
           {
               success = true;
           }

            db.close();
        }
        catch (SQLException e)
        {
            Log.d("ADD EVENT ERROR: ", e.getMessage() );
        }


        return success;
    }



    public int getRecordCount(){

            SQLiteDatabase db = this.getWritableDatabase();

            String QUERY_COUNT_ROWS = "SELECT count(*) FROM " + TABLE_NAME;

            cursor = db.rawQuery(QUERY_COUNT_ROWS, null);
            cursor.moveToFirst();
            int rowCount = cursor.getInt(0);


            Log.d("ROW COUNT: ", Integer.toString(rowCount));
            db.close();


        return rowCount;



    }


    public ArrayList<Event> getAllEvents() {

        SQLiteDatabase db = this.getWritableDatabase();

        // query(String table, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy)
        cursor = db.rawQuery("select * from " + TABLE_NAME, null);;

        //write contents of Cursor to list
        Events = new ArrayList<Event>();

            while ( cursor.moveToNext() )
            {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String desc = cursor.getString(cursor.getColumnIndex(KEY_DESC));
                String start = cursor.getString(cursor.getColumnIndex(KEY_START));
                String end = cursor.getString(cursor.getColumnIndex(KEY_END));
                String addr = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                String city = cursor.getString(cursor.getColumnIndex(KEY_CITY));
                String state = cursor.getString(cursor.getColumnIndex(KEY_STATE));
                String link = cursor.getString(cursor.getColumnIndex(KEY_LINK));
                String nmber = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));



                Events.add(new Event(id, name, desc, start, end, addr, city, state, link, nmber));

                Log.d("EVENT: ", Events.get(id - 1).toString() );
            }
            db.close();



        return Events;

    }

    public boolean addItem(Item item){
        boolean success = false;

        try
        {
            SQLiteDatabase db = this.getWritableDatabase();

            values = new ContentValues();
            values.put(KEY_ITEM_NAME, item.getItemName() );
            values.put(KEY_ITEM_DESC, item.getItemDescription() );
            values.put(KEY_ITEM_PRICE, item.getItemPrice() );
            values.put(KEY_ITEM_CITY, item.getItemCity() );
            values.put(KEY_ITEM_STATE, item.getItemState() );
            values.put(KEY_ITEM_NUMBER, item.getItemContactNumber() );

            // insert function returns -1 if an error occurred OR ID no. of inserted record on success
            if( db.insert(ITEM_TABLE_NAME, null, values) != -1 )
            {
                success = true;
            }

            db.close();
        }
        catch (SQLException e)
        {
            Log.d("ADD ITEM ERROR: ", e.getMessage() );
        }


        return success;
    }



    public int getItemRecordCount(){

        SQLiteDatabase db = this.getWritableDatabase();

        String QUERY_COUNT_ROWS = "SELECT count(*) FROM " + ITEM_TABLE_NAME;

        cursor = db.rawQuery(QUERY_COUNT_ROWS, null);
        cursor.moveToFirst();
        int rowCount = cursor.getInt(0);


        Log.d("ROW COUNT: ", Integer.toString(rowCount));
        db.close();


        return rowCount;



    }


    public ArrayList<Item> getAllItems() {

        SQLiteDatabase db = this.getWritableDatabase();

        // query(String table, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy)
        cursor = db.rawQuery("select * from " + ITEM_TABLE_NAME, null);;

        //write contents of Cursor to list
        Items = new ArrayList<Item>();

        while ( cursor.moveToNext() )
        {
            int itemid = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID));
            String itemname = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME));
            String itemdesc = cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESC));
            String itemprice = cursor.getString(cursor.getColumnIndex(KEY_ITEM_PRICE));
            String itemcity = cursor.getString(cursor.getColumnIndex(KEY_ITEM_CITY));
            String itemstate = cursor.getString(cursor.getColumnIndex(KEY_ITEM_STATE));
            String itemnmber = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NUMBER));



            Items.add(new Item(itemid, itemname, itemdesc, itemprice, itemcity, itemstate, itemnmber));

            Log.d("ITEMS: ", Items.get(itemid - 1).toString() );
        }
        db.close();



        return Items;

    }


}
