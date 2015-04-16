package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Food extends Event{

    public Food() {
        this(null, "", 0.0f);
    }

    public Food(Date date, String location, float duration) {
        super(date, location, "food", duration);
    }
}
