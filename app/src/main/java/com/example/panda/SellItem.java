package com.example.panda;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


/*
   This class uses the sell_item layout to accept item data from the users.
   It will then call the add item function in the DB Handler class to add the item to the DB.
 */

public class SellItem extends Activity {


    private String userItemTitle;
    //private String userEventAddress;
    private String userItemCity;
    private String userItemState;
    private String userItemDescription;
    private String userItemPrice;
    //private String userEventWebsite;
    private String userItemContactNumber;
    private String userItemStartTime;
    private String userItemEndTime;

    private Context context;


    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_item);

        Button btnSellItem = (Button) findViewById(R.id.btnSellItem);
        btnSellItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SellItem(view);
            }
        });

        this.context = this;

    }

    public void SellItem(View view){

        // get the item details entered an entered into an item object
        userItemTitle = ( (EditText) findViewById(R.id.txtItem) ).getText().toString();
        //userAddress = ( (EditText) findViewById(R.id.txtAddress) ).getText().toString();
        userItemCity = ( (EditText) findViewById(R.id.txtItemCity) ).getText().toString();
        userItemState = ( (EditText) findViewById(R.id.txtItemState) ).getText().toString();
        userItemDescription = ( (EditText) findViewById(R.id.txtItemDescription) ).getText().toString();
        userItemPrice= ( (EditText) findViewById(R.id.txtItemPrice) ).getText().toString();
        //userEventStartTime = ( (EditText) findViewById(R.id.txtStartTime) ).getText().toString();
        //userEventEndTime = ( (EditText) findViewById(R.id.txtEndTime) ).getText().toString();
        //userEventWebsite = ( (EditText) findViewById(R.id.txtWebsite) ).getText().toString();
        userItemContactNumber = ( (EditText) findViewById(R.id.itemContactNumber) ).getText().toString();
        Item userItem = new Item(userItemTitle, userItemDescription, userItemPrice, userItemCity,
                                    userItemState, userItemContactNumber);


        // pass event object to the db handler class
        dbHandler = new DBHandler(this);
        boolean success = dbHandler.addItem(userItem);

        AlertDialog dialog = new AlertDialog.Builder(SellItem.this).create();

        LayoutInflater factory = LayoutInflater.from(SellItem.this);
        View dialogView = factory.inflate(R.layout.dialog_image_container, null);
        dialog.setView(dialogView);

        ImageView imgAlert = (ImageView) dialogView.findViewById(R.id.imgAlert);
        imgAlert.setBackgroundResource(R.drawable.alert_animation);

        final AnimationDrawable frameAnimation = (AnimationDrawable) imgAlert.getBackground();



        if(!success)
        {
            // this will show up if there was an error adding the item to the database
            dialog.setTitle("Sorry");
            dialog.setMessage("There was an error creating your post.");

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",
                                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Go back to item list
                    Intent intentItemList = new Intent(SellItem.this, MainActivity.class);
                    startActivity(intentItemList);
                }
            });

            dialog.show();
        }
        else
        {
            dialog.setTitle("Congrats!");
            dialog.setMessage("Your Item sale was created");

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",
                                new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    // Go back to item list
                    Intent intentItemList = new Intent(SellItem.this, MainActivity.class);
                    startActivity(intentItemList);
                }
            });
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    frameAnimation.start();
                }
            });
            dialog.show();

        }

    }

}
