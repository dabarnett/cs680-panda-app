/*package com.example.panda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class ItemDBHandler extends SQLiteOpenHelper {
    // Database properties
    private static final String DATABASE_NAME = "pandaDBItems";
    private static final String TABLE_NAME = "Items";
    private static final int DATABASE_VERSION = 1;

    private Cursor cursor;
    private ContentValues values;


    public static final String KEY_ITEM_ID = "id";
    public static final String KEY_ITEM_NAME = "name";
    public static final String KEY_ITEM_PRICE = "price";
    public static final String KEY_ITEM_DESC = "description";
    public static final String KEY_ITEM_CITY = "city";
    public static final String KEY_ITEM_STATE = "state";
    public static final String KEY_ITEM_NUMBER = "contact_number";


    public static final String QUERY_CREATE_TABLE = "CREATE TABLE " + ITEM_TABLE_NAME + " ("
            + KEY_ITEM_ID + " INTEGER primary key autoincrement,"
            + KEY_ITEM_NAME + " TEXT, "
            + KEY_ITEM_PRICE + " TEXT, "
            + KEY_ITEM_DESC + " TEXT, "
            + KEY_ITEM_CITY + " TEXT, "
            + KEY_ITEM_STATE + " TEXT, "
            + KEY_ITEM_NUMBER + " TEXT)" ;


    private ArrayList<Item> Items;



    public ItemDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }


    //if older db version exists, drop it and replace
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        Log.d("SQLiteDemo", "onUpgrade: Version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    /*
    *    This function is for demo purposes
    *    It populates the database with some events to get the user started
    */
/*
    public void initialiseDatabase() {

        // create some new events using the custom event class (Event.java)
        // One of the constructors accepts the name, description, address (street address, city, state) and website link of an event
        addItem( new Item( "Tape Drive",
                "LTO 4 Tape Drive.",
                "$400",
                "Boston",
                "MA",
                "781-555-2113") );

        addItem( new Item( "Tape Drive",
                "LTO 4 Tape Drive.",
                "$400",
                "Boston",
                "MA",
                "781-555-2113") );

        addItem( new Item( "Tape Drive",
                "LTO 4 Tape Drive.",
                "$400",
                "Boston",
                "MA",
                "781-555-2113") );

        addItem( new Item( "Tape Drive",
                "LTO 4 Tape Drive.",
                "$400",
                "Boston",
                "MA",
                "781-555-2113") );
    }


    /*
    *    addEvent method accepts an event class instance and
    *    inserts it into the database
    */
/*
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
            if( db.insert(TABLE_NAME, null, values) != -1 )
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


    public ArrayList<Item> getAllItems() {

        SQLiteDatabase db = this.getWritableDatabase();

        // query(String table, String[] columns, String whereClause, String[] whereArgs, String groupBy, String having, String orderBy)
        cursor = db.rawQuery("select * from " + TABLE_NAME, null);;

        //write contents of Cursor to list
        Items = new ArrayList<Item>();

        while ( cursor.moveToNext() )
        {
            int itemid = cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID));
            String itemname = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME));
            String itemdesc = cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESC));
            String itemprice = cursor.getString(cursor.getColumnIndex(KEY_ITEM_PRICE));
            String itemcity = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME));
            String itemstate = cursor.getString(cursor.getColumnIndex(KEY_ITEM_STATE));
            String itemnmber = cursor.getString(cursor.getColumnIndex(KEY_ITEM_NUMBER));



            Items.add(new Item(itemid, itemname, itemdesc, itemprice, itemcity, itemstate, itemnmber));

            Log.d("ITEMS: ", Items.get(itemid - 1).toString() );
        }
        db.close();



        return Items;

    }


}
*/