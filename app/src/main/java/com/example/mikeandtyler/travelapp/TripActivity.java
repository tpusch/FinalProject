package com.example.mikeandtyler.travelapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class TripActivity extends FragmentActivity implements CreateTripFrag.OnFragmentInteractionListener, TripListFrag.OnFragmentInteractionListener{

    FragmentTransaction fragmentTransaction;
    TripListFrag tripListFrag;

    CreateSequentialFile database;
    List<Trip> trips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        tripListFrag = new TripListFrag();

        database = new CreateSequentialFile();
        database.openReadFile(this);
        trips = database.loadTrips();
        database.closeReadFile();

        Bundle args = new Bundle();
        args.putInt("size", trips.size());
        for(int i = 0; i < trips.size(); i++){
            args.putSerializable("trip"+i, trips.get(i));
        }
        tripListFrag.setArguments(args);

        Log.d("size", String.valueOf(trips.size()));
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.TripLayout, tripListFrag, "TRIP_LIST");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    protected void onStop(){
        super.onStop();
        database.openWriteFile(this);
        for(int i = 0; i < trips.size(); i++){
            database.saveTrip(trips.get(i));
        }
        database.closeWriteFile();
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

    public void newTrip(View view){
        CreateTripFrag createTripFrag = new CreateTripFrag();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.TripLayout, createTripFrag, "CREATE_TRIP");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void submitTrip(View view){
        EditText editText = (EditText) findViewById(R.id.editText);
        Trip trip = new Trip(null, null, null, editText.getText().toString());
        trips.add(trip);
        Bundle args = new Bundle();
        args.putInt("size", trips.size());
        for(int i = 0; i < trips.size(); i++){
            args.putSerializable("trip"+i, trips.get(i));
        }
        tripListFrag.setArguments(args);
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.TripLayout, tripListFrag, "TRIP_LIST");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void nextList(View view){

    }

    public void onFragmentInteraction(Uri uri){

    }

    public void onTripFragmentInteraction(Trip trip){
        Intent i = new Intent(this, EventActivity.class);
        i.putExtra("trip", trip);
        startActivity(i);
    }
}
