package com.example.mikeandtyler.travelapp;

import java.util.Date;

/**
 * Created by XMZALA on 4/16/15.
 */
public class Train extends Travel{

    private int trainNumber;

    public Train(Date date, String fromCity, String toCity, float duration, int trainNumber) {
        super(date, fromCity, toCity, duration, "train");
        this.trainNumber = trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }
}
