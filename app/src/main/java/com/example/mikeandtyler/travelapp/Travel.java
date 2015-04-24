package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public abstract class Travel extends Event{

    private String toCity, travelType;

    public Travel() {
        this(null, "", "", "", "", "");
    }

    public Travel(Date date, String fromCity, String toCity, String time, String travelType, String info) {
        super(date, fromCity, "Travel", time, info);
        this.toCity = toCity;
        this.travelType = travelType;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getToCity() {
        return toCity;
    }

    public String getTravelType() {
        return travelType;
    }
}
