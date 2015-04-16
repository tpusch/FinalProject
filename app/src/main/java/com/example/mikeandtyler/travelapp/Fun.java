package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Fun extends Event{

    public Fun() {
        this(null, "", 0.0f);
    }

    public Fun(Date date, String location, float duration){
        super(date, location, "fun", duration);
    }
}
