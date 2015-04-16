package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Car extends Travel{

    public Car(Date date, String fromCity, String toCity, float duration) {
        super(date, fromCity, toCity, duration, "car");
    }
}
