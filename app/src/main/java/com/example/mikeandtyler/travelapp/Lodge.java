package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Lodge extends Event{

    private Date endDate;

    public Lodge() {
        this(null, null, "", "", "");
    }

    public Lodge(Date startDate, Date endDate, String location, String time, String info) {
        super(startDate, location, "Lodging", time, info);
        this.endDate = endDate;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public Date getEndDate() {
        return endDate;
    }
}
