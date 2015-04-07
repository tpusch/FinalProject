package com.example.mikeandtyler.travelapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class TripList extends FragmentActivity implements CreateTrip.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
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
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CreateTrip createTrip = new CreateTrip();
        fragmentTransaction.add(R.id.TripLayout, createTrip, "CREATE_TRIP");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void submitTrip(View view){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(getFragmentManager().findFragmentByTag("CREATE_TRIP"));
        fragmentTransaction.commit();
    }

    public void nextList(View view){
        Intent i = new Intent(this, ItineraryList.class);
        startActivity(i);
    }

    public void onFragmentInteraction(Uri uri){

    }
}
