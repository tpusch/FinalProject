package com.example.mikeandtyler.travelapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public abstract class Event implements Serializable {

    private Date date;

    private String location, type, info, time;

    public Event() {
        this(null, "", "", "", "");
    }

    public Event(Date date, String location, String type, String time, String info) {
        this.date = date;
        this.location = location;
        this.type = type;
        this.time = time;
        this.info = info;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public String getInfo() {
        return info;
    }

//    @Override
//    public int compareTo(Event event) {
//        if (getDate() == null || event.getDate() == null)
//            return 0;
//        return getDate().compareTo(event.getDate());
//    }

    @Override
    public String toString() {
        return ""+location+"  "+date.toString().substring(0,10);
    }
}
