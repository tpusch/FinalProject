package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Train extends Travel{

    private String trainNumber;

    public Train() {
        this(null, "", "", "", "", "");
    }

    public Train(Date date, String fromCity, String toCity, String time, String trainNumber, String info) {
        super(date, fromCity, toCity, time, "Train", info);
        this.trainNumber = trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }
}
