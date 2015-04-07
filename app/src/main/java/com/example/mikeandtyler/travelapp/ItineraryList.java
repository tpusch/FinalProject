package com.example.mikeandtyler.travelapp;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ItineraryList extends FragmentActivity implements EventViewer.OnFragmentInteractionListener, CreateEvent.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_itinerary_list, menu);
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

    public void submitEvent(View view){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(getFragmentManager().findFragmentByTag("CREATE_EVENT"));
        fragmentTransaction.commit();
    }

    public void newEvent(View view){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CreateEvent createEvent = new CreateEvent();
        fragmentTransaction.add(R.id.EventLayout, createEvent, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void editEvent(View view){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        CreateEvent createEvent = new CreateEvent();
        fragmentTransaction.replace(R.id.EventLayout, createEvent, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void seeEvent(View view){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        EventViewer eventViewer = new EventViewer();
        fragmentTransaction.add(R.id.EventLayout, eventViewer, "VIEW_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(Uri uri){

    }
}
