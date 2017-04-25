package com.example.panda;

import java.text.SimpleDateFormat;


public class Event {

    private int id;
    private String eventName;
    private String eventDescription;
    private String startDateTime;
    private String endDateTime;
    private String streetAddress;
    private String city;
    private String state;
    private String websiteLink;
    private String contactNumber;


    public Event(int id, String eventName, String eventDescription, String startDateTime, String endDateTime, String streetAddress, String city, String state, String websiteLink, String contactNumber ){
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.websiteLink = websiteLink;
        this.contactNumber = contactNumber;
    }

    public Event(String eventName, String eventDescription, String startDateTime, String endDateTime, String streetAddress, String city, String state, String websiteLink, String contactNumber){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.websiteLink = websiteLink;
        this.contactNumber = contactNumber;
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

    public String getStreetAddress(){
        return streetAddress;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getWebsiteLink(){
        return websiteLink;
    }

    public String getContactNumber() {return contactNumber; }

    public String getStartDateTime() {return startDateTime; }

    public String getEndDateTime() {return endDateTime; }


    public String toString(){
        return "id: " + id + ", eventName: " + eventName + ", streetAddress: " + streetAddress + ", websiteLink: " + websiteLink + ", contactNumber: " + contactNumber + ", startDateTime" + startDateTime + ", endDateTime" + endDateTime;
    }



}
