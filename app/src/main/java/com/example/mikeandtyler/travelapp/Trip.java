package com.example.mikeandtyler.travelapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Trip {

    private List<Event> events = new ArrayList<>();
    private Date startDate, endDate;
    private List<String> locations = new ArrayList<>();

    public Trip(List<Event> events, Date startDate, Date endDate, List<String> locations) {
        this.events = events;
        this.startDate = startDate;
        this.endDate = endDate;
        this.locations = locations;
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

    public void addLocation(String location) {
        locations.add(location);
    }

    public void removeLocation(String location) {
        locations.remove(location);
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

    public List<String> getLocations() {
        return locations;
    }
}
