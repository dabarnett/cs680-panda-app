package com.example.panda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


// This adapter allows us to customise the event listview
// We can specify the event data to be placed in the listview layout

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> items;
    private int lastPosition = -1;
    private Typeface tf;




    public ItemAdapter(Context context, int resource, ArrayList<Item> data) {
        super(context, resource, data);
        this.context = context;
        this.items = data;
        this.tf = Typeface.createFromAsset(context.getAssets(), "fonts/Muli-SemiBold.ttf");
    }


    @Override
    public View getView(final int position, View row, ViewGroup parent) {

        final Item item = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        // using the event_list_item.xml as a template
        if (row == null)
        {
            row = LayoutInflater.from( getContext()).inflate(R.layout.item_list_item, parent, false);
        }


        lastPosition = position;

        TextView tvItemTtle = (TextView) row.findViewById(R.id.ItemTitle);
        tvItemTtle.setText( item.getItemName() );
        tvItemTtle.setTypeface(tf);

        TextView tvItemCityState = (TextView) row.findViewById(R.id.ItemCityState);
        tvItemCityState.setText( item.getItemCity() + ", " + item.getItemState() );

        TextView tvItemPrice = (TextView) row.findViewById(R.id.ItemPrice);
        tvItemPrice.setText( item.getItemPrice());

        ImageView imgItem = (ImageView) row.findViewById(R.id.bgitem);
        if( item.getItemImage() == null || item.getItemImage().isEmpty() )
        {
            item.setItemImage( "default_img" );
        }
        // the line below will find the id of the image resource with a matching name and
        //then get set the resource with the matching id to the imageview
        int resourceID = context.getResources().getIdentifier( item.getItemImage(), "drawable",
                context.getPackageName() );
        imgItem.setImageResource( resourceID );


        Button btnItemContact = (Button) row.findViewById(R.id.btnItemContact);

        btnItemContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer(item.getItemContactNumber());
            }
            });


        return row;

    }


    public void openDialer(String itemcontactNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + itemcontactNumber));
        context.startActivity(intent);
    }

}
