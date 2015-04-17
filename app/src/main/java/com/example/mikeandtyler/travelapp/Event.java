package com.example.mikeandtyler.travelapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public abstract class Event implements Serializable {

    private Date date;
    private String location, type;
    private float duration;

    public Event() {
        this(null, "", "", 0.0f);
    }

    public Event(Date date, String location, String type, float duration) {
        this.date = date;
        this.location = location;
        this.type = type;
        this.duration = duration;
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

    public void setDuration(float duration) {
        this.duration = duration;
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

    public float getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return date.toString();
    }
}
