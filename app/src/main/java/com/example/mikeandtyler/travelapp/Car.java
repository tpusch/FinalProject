package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Car extends Travel{

    public Car() {
        this(null, "", "", "", "");
    }

    public Car(Date date, String fromCity, String toCity, String time, String info) {
        super(date, fromCity, toCity, time, "Car", info);
    }
}
