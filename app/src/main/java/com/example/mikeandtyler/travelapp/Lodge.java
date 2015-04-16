package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Lodge extends Event{

    public Lodge(Date date, String location, float duration) {
        super(date, location, "lodge", duration);
    }
}
