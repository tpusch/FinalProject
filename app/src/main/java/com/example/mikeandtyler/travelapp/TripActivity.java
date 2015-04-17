package com.example.mikeandtyler.travelapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class TripActivity extends FragmentActivity implements CreateTripFrag.OnFragmentInteractionListener, TripListFrag.OnFragmentInteractionListener{

    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        TripListFrag tripListFrag = new TripListFrag();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.TripLayout, tripListFrag, "TRIP_LIST");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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
        fragmentTransaction = getFragmentManager().beginTransaction();
        TripListFrag tripListFrag = new TripListFrag();
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
