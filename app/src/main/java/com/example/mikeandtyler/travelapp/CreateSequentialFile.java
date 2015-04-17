package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by pusc1tyl on 4/16/15.
 */
public class CreateSequentialFile {

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public CreateSequentialFile(){}

    public void openWriteFile(Activity activity) {
        try {
            output = new ObjectOutputStream(activity.openFileOutput("trips.ser", Context.MODE_PRIVATE));
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public void openReadFile(Activity activity){
        try {
            input = new ObjectInputStream(activity.openFileInput("trips.ser"));
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public ArrayList<Trip> loadTrips() {
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        try{
            while(true){
                   Trip trip = (Trip) input.readObject();
                   tripList.add(trip);
            }
        }
        catch(EOFException endOfFileException){
            Log.d("error", endOfFileException.toString());
        }
        catch(ClassNotFoundException classNotFoundException){
            Log.d("error", classNotFoundException.toString());
        }
        catch(IOException ioException){
            Log.d("error", ioException.toString());
        }
//        Date d = new Date();
//        Event event = new Food(d, "string", 2.2f);
//        List<Event> events = new ArrayList<>();
//        events.add(event);
//        Trip trip = new Trip(events, d, d, "first Trip");
//        tripList.add(trip);
        return tripList;
    }

    public void saveTrip(Trip trip) {
        try {
            output.writeObject(trip);
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public void closeWriteFile() {
        try {
            if (output != null) {
                output.close();
            }
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public void closeReadFile() {
        try{
            if(input != null){
                input.close();
            }
        }
        catch(IOException ioException){
            Log.d("error", ioException.toString());
        }
    }
}
