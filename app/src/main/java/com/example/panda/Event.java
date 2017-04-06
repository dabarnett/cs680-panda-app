package com.example.panda;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DTech on 05/04/2017.
 */

public class Event {

    private int id;
    private String eventName;
    private String eventDescription;
    private SimpleDateFormat startDateTime;
    private SimpleDateFormat endDateTime;
    private String address;
    private String websiteLink;


    public Event( int id, String eventName, String eventDescription, String address, String websiteLink ){
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.address = address;
        this.websiteLink = websiteLink;
    }

    public Event( String eventName, String eventDescription, String address, String websiteLink ){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.address = address;
        this.websiteLink = websiteLink;
    }

    public int getEventID(){
        return id;
    }

    public String getEventName(){
        return eventName;
    }

    public String getEventDescription(){
        return eventDescription;
    }

    public String getAddress(){
        return address;
    }

    public String getWebsiteLink(){
        return websiteLink;
    }


    public String toString(){
        return "id: " + id + ", eventName: " + eventName + ", address: " + address + ", websiteLink: " + websiteLink;
    }


}
