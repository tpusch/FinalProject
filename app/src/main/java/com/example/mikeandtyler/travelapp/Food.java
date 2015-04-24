package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Food extends Event{

    public Food() {
        this(null, "", "", "");
    }

    public Food(Date date, String location, String time, String info) {
        super(date, location, "Dining", time, info);
    }
}
