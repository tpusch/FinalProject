package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Flight extends Travel{

    private int flightNumber;

    public Flight() {
        this(null, "", "", 0.0f, 0);
    }

    public Flight(Date date, String fromCity, String toCity, float duration, int flightNumber) {
        super(date, fromCity, toCity, duration, "flight");
        this.flightNumber = flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getFlightNumber() {
        return flightNumber;
    }
}
