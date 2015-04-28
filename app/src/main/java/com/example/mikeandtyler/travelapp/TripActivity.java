package com.example.mikeandtyler.travelapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class TripActivity extends FragmentActivity implements CreateTripFrag.OnFragmentInteractionListener, TripListFrag.OnFragmentInteractionListener{

    FragmentTransaction fragmentTransaction;
    TripListFrag tripListFrag;

    CreateSequentialFile database;
    boolean newTrip;
    Trip currentTrip;
    List<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);
        database = new CreateSequentialFile();
    }

    protected void onStart(){
        super.onStart();

        database.openReadFile(this);
        trips = database.loadTrips();
        database.closeReadFile();

        Collections.sort(trips, new Comparator<Trip>() {
            public int compare(Trip o1, Trip o2) {
                if (o1.getStartDate() == null || o2.getStartDate() == null)
                    return 0;
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        tripListFrag = new TripListFrag();

        Bundle args = new Bundle();
        args.putInt("size", trips.size());
        for(int i = 0; i < trips.size(); i++){
            args.putSerializable("trip"+i, trips.get(i));
        }

        tripListFrag.setArguments(args);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TripLayout, tripListFrag, "TRIP_LIST");
        fragmentTransaction.commit();
    }

    //Back Button press handler, goes back through fragments, or to the previous activity
    public void onBackPressed()
    {
        FragmentManager fm = this.getFragmentManager();
        if(fm.getBackStackEntryCount() < 1) {
            super.onBackPressed();
        }
        else {
            fm.popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //onClick function: create a new Trip from the Trip List
    public void newTrip(View view){
        CreateTripFrag createTripFrag = new CreateTripFrag();
        newTrip = true;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TripLayout, createTripFrag, "CREATE_TRIP");
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            fragmentTransaction.addToBackStack("tripList");
        }
        fragmentTransaction.commit();
    }

    //onClick function: submit the trip from Create Trip Frag
    public void submitTrip(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        DatePicker startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        DatePicker endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);

        Date startDate = CreateSequentialFile.getDateFromDatePicker(startDatePicker);
        Date endDate = CreateSequentialFile.getDateFromDatePicker(endDatePicker);

        //If we are creating a new trip from scratch
        if(newTrip){

            Trip trip = new Trip(null, startDate, endDate, editText.getText().toString());
            trips.add(trip);
        //If we are editing an existing trip
        }else{
            trips.remove(currentTrip);
            Trip trip = new Trip(currentTrip.getEvents(), startDate, endDate, editText.getText().toString());
            trips.add(trip);
        }

        //Reset the trips by date
        Collections.sort(trips, new Comparator<Trip>() {
            public int compare(Trip o1, Trip o2) {
                if (o1.getStartDate() == null || o2.getStartDate() == null)
                    return 0;
                return o1.getStartDate().compareTo(o2.getStartDate());
            }
        });

        //Save
        database.openWriteFile(this);
        for(int i = 0; i < trips.size(); i++){
            database.saveTrip(trips.get(i));
        }
        database.closeWriteFile();

        //Restart and move to the Trip List Fragment with the new trip list
        Bundle args = new Bundle();
        args.putInt("size", trips.size());
        for(int i = 0; i < trips.size(); i++){
            args.putSerializable("trip"+i, trips.get(i));
        }
        tripListFrag = new TripListFrag();
        tripListFrag.setArguments(args);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TripLayout, tripListFrag, "TRIP_LIST");
        getFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }

    //Unused onFragmentInteraction from CreateTripFrag
    public void onFragmentInteraction(Uri uri){}

    //Fragment Interaction from TripListFrag
    //Starts the EventActivity with the selected trip
    public void onTripFragmentInteraction(Trip trip){
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("trip", trip);
        startActivity(i);
    }

    //Fragment Interaction from TripListFrag
    //Removes an existing trip if selected on Long press
    public void removeTrip(Trip trip){
        trips.remove(trip);
        database.openWriteFile(this);
        for(int i = 0; i < trips.size(); i++){
            database.saveTrip(trips.get(i));
        }
        database.closeWriteFile();
    }

    //Fragment Interaction from TripListFrag
    //starts the CreateTrip fragment with the selected trip
    public void editTrip(Trip trip){
        CreateTripFrag createTripFrag = new CreateTripFrag();
        Bundle args = new Bundle();

        //sets the current trip to the selected trip from the trip list
        currentTrip = trip;

        //notifies the createTripFrag that we are going to edit a trip
        newTrip = false;
        args.putSerializable("newTrip", false);
        createTripFrag.setArguments(args);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TripLayout, createTripFrag, "CREATE_TRIP");
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            fragmentTransaction.addToBackStack("tripList");
        }
        fragmentTransaction.commit();
    }

    //Fragment Interaction from CreateTripFrag
    //Fills the fragment with existing trip information
    public void fillTrip(){
        DatePicker startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        DatePicker endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);
        EditText nameOfTripEditText = (EditText) findViewById(R.id.editText);

        nameOfTripEditText.setText(currentTrip.getName());

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTrip.getStartDate());
        startDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        cal.setTime(currentTrip.getEndDate());
        endDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

    }
}
