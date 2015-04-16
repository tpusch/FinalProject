package com.example.mikeandtyler.travelapp;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class EventActivity extends FragmentActivity implements EventViewerFrag.OnFragmentInteractionListener, CreateEventFrag.OnFragmentInteractionListener, EventListFrag.OnFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    EventListFrag eventListFrag;
    EventViewerFrag eventViewerFrag;
    CreateEventFrag createEventFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventListFrag = new EventListFrag();
        eventViewerFrag = new EventViewerFrag();
        createEventFrag = new CreateEventFrag();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.EventLayout, eventListFrag, "LIST_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventLayout, eventListFrag, "LIST_EVENT");
        fragmentTransaction.commit();
    }

    public void newEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
//fuck git
    public void editEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void seeEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventLayout, eventViewerFrag, "VIEW_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(Uri uri){

    }
}
