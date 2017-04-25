package com.example.panda;

import java.text.SimpleDateFormat;


public class Item {

    private int id;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemcity;
    private String itemstate;
    private String itemcontactNumber;


    public Item(int id, String itemName, String itemDescription, String itemPrice, String itemcity, String itemstate, String itemcontactNumber ){
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemcity = itemcity;
        this.itemstate = itemstate;
        this.itemcontactNumber = itemcontactNumber;
    }

    public Item(String itemName, String itemDescription, String itemPrice, String itemcity, String itemstate, String itemcontactNumber ){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemcity = itemcity;
        this.itemstate = itemstate;
        this.itemcontactNumber = itemcontactNumber;
    }

    public int getItemID(){
        return id;
    }

    public String getItemName(){
        return itemName;
    }

    public String getItemDescription(){
        return itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }


    public String getItemCity(){
        return itemcity;
    }

    public String getItemState(){
        return itemstate;
    }


    public String getItemContactNumber() {return itemcontactNumber; }



    public String toString(){
        return "id: " + id + ", ItemName: " + itemName + ", contactNumber: " + itemcontactNumber + ", itemPrice: " + itemPrice;
    }



}
