package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Flight extends Travel{

    private String flightNumber;

    public Flight() {
        this(null, "", "", "", "", "");
    }

    public Flight(Date date, String fromCity, String toCity, String time, String flightNumber, String info) {
        super(date, fromCity, toCity, time, "Flight", info);
        this.flightNumber = flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
