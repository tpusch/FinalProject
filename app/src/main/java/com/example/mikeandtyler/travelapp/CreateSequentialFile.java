package com.example.mikeandtyler.travelapp;

import android.util.Log;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by pusc1tyl on 4/16/15.
 */
public class CreateSequentialFile {

    private ObjectOutputStream output;
    private ObjectInputStream input;

    public CreateSequentialFile(){

    }

    public void openWriteFile() {
        try {
            output = new ObjectOutputStream(new FileOutputStream("trips.ser"));
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public void openReadFile(){
        try {
            input = new ObjectInputStream(new FileInputStream("trips.ser"));
        }
        catch(IOException ioException) {
            Log.d("error", ioException.toString());
        }
    }

    public ArrayList<Trip> loadTrips() {
        ArrayList<Trip> tripList = new ArrayList<Trip>();
        Trip trip;
        try{
            while(true){
                trip = (Trip) input.readObject();
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
