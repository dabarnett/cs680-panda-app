package com.example.panda;


public class Item {

    private int id;
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String itemcity;
    private String itemstate;
    private String itemcontactNumber;
    private String itemImage;


    public Item(int id, String itemName, String itemDescription, String itemPrice, String
                    itemcity, String itemstate, String itemcontactNumber, String itemImage ){
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemcity = itemcity;
        this.itemstate = itemstate;
        this.itemcontactNumber = itemcontactNumber;
        this.itemImage = itemImage;
    }

    public Item(String itemName, String itemDescription, String itemPrice, String itemcity,
                String itemstate, String itemcontactNumber ){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemcity = itemcity;
        this.itemstate = itemstate;
        this.itemcontactNumber = itemcontactNumber;
    }


    public Item(String itemName, String itemDescription, String itemPrice, String itemcity,
                    String itemstate, String itemcontactNumber, String itemImage ){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemcity = itemcity;
        this.itemstate = itemstate;
        this.itemcontactNumber = itemcontactNumber;
        this.itemImage = itemImage;
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

    public String getItemImage() {return itemImage; }

    public void setItemImage(String itemImage){
        this.itemImage = itemImage;
    }


    public String toString(){
        return "id: " + id + ", ItemName: " + itemName + ", contactNumber: " + itemcontactNumber
                + ", itemPrice: " + itemPrice;
    }



}
