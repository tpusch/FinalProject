package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Fun extends Event{

    public Fun() {
        this(null, "", "", "");
    }

    public Fun(Date date, String location, String time, String info){
        super(date, location, "Event", time, info);
    }
}
