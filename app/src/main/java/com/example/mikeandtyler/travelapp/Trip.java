package com.example.mikeandtyler.travelapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Trip implements Serializable{

    private List<Event> events = new ArrayList<>();
    private Date startDate, endDate;
    private String location;

    public Trip() {
        this(null, null, null, "");
    }

    public Trip(List<Event> events, Date startDate, Date endDate, String location) {
        this.events = events;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Event> getEvents() {
        return events;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }
}
