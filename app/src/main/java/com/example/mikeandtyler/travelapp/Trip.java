package com.example.mikeandtyler.travelapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Trip implements Serializable{

    private List<Event> events;
    private Date startDate, endDate;
    private String name;
    private UUID id;

    public Trip() {
        this(null, null, null, "");
        this.id = UUID.randomUUID();
    }

    public Trip(List<Event> events, Date startDate, Date endDate, String name) {
        if(events == null) {
            this.events = new ArrayList();
        }
        else {
            this.events = events;
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.id = UUID.randomUUID();
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

    public void setName(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        String stringDate = startDate.toString();
        String eDate = endDate.toString();
        return name + " : " + stringDate.substring(0,10) + "," + stringDate.substring(stringDate.length()-5, stringDate.length())+
                " - " + eDate.substring(0,10) + "," + eDate.substring(eDate.length()-5, eDate.length());
    }
}
