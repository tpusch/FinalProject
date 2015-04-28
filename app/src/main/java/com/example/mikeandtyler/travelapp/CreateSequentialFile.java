package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by pusc1tyl on 4/16/15.
 */
public class CreateSequentialFile {

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public CreateSequentialFile(){}

    //Opens or creates a file for writing, takes an android activity to open
    public void openWriteFile(Activity activity) {
        try {
            output = new ObjectOutputStream(activity.openFileOutput("trips.ser", Context.MODE_PRIVATE));
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    //Opens but does not create a file for reading, takes an android activity to open
    public boolean openReadFile(Activity activity){
        try {
            input = new ObjectInputStream(activity.openFileInput("trips.ser"));
            return true;
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
            return false;
        }
    }

    //Loads all trips from a serializable file and returns them in an array list
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
        return tripList;
    }

    //Saves a single trip to a previously opened write file
    public void saveTrip(Trip trip) {
        try {
            output.writeObject(trip);
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    //Closes a file opened for writing
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

    //Closes a file opened for reading
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

    //Helper function to retrieve the date from a provided date picker widget, returns a date
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
