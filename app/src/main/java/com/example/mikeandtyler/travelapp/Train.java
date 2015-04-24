package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Train extends Travel{

    private int trainNumber;

    public Train() {
        this(null, "", "", "", 0, "");
    }

    public Train(Date date, String fromCity, String toCity, String time, int trainNumber, String info) {
        super(date, fromCity, toCity, time, "Train", info);
        this.trainNumber = trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }
}
