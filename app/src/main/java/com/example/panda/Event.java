package com.example.panda;


public class Event {

    private int id;
    private String eventName;
    private String eventDescription;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String streetAddress;
    private String city;
    private String state;
    private String websiteLink;
    private String contactNumber;
    private String starred;
    private String imagePath;




    public Event(int id, String eventName, String eventDescription, String eventDate, String startTime, String endTime, String streetAddress, String city, String state, String websiteLink, String contactNumber, String starred, String imagePath){
        this.id = id;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.websiteLink = websiteLink;
        this.contactNumber = contactNumber;
        this.starred = starred;
        this.imagePath = imagePath;
    }


    public Event(String eventName, String eventDescription, String eventDate, String startTime, String endTime, String streetAddress, String city, String state, String websiteLink, String contactNumber, String starred  ){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.websiteLink = websiteLink;
        this.contactNumber = contactNumber;
        this.starred = starred;
    }


    public Event(String eventName, String eventDescription, String eventDate, String startTime, String endTime, String streetAddress, String city, String state, String websiteLink, String contactNumber, String starred, String imagePath ){
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.websiteLink = websiteLink;
        this.contactNumber = contactNumber;
        this.starred = starred;
        this.imagePath = imagePath;
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

    public String getDate() {return eventDate; }

    public String getStartTime() {return startTime; }

    public String getEndTime() {return endTime; }

    public String getStarredStatus() {return starred; }

    public String getImagePath() {return imagePath; }

    public void setStarredStatus(String starred){
        this.starred = starred;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    // this function is mostly for testing purposes. It will print the value of each event
    // object in a string.
    public String toString(){
        return "id: " + id + ", eventName: " + eventName + ", streetAddress: " + streetAddress +
                ", websiteLink: " + websiteLink + ", contactNumber: " + contactNumber + ", date: " + eventDate + ", startTime: " + startTime + ", endTime: " + endTime + ", imagePath: " + imagePath + ", starred: " + starred;
    }



}
