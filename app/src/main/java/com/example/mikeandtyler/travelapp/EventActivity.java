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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EventActivity extends FragmentActivity implements EventViewerFrag.OnFragmentInteractionListener, CreateEventFrag.OnFragmentInteractionListener, EventListFrag.OnFragmentInteractionListener {

    FragmentTransaction fragmentTransaction;
    EventListFrag eventListFrag;
    EventViewerFrag eventViewerFrag;
    CreateEventFrag createEventFrag;
    Boolean newEvent;

    Event currentEvent;

    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventListFrag = new EventListFrag();
        trip = (Trip) getIntent().getSerializableExtra("trip");
        Bundle args = new Bundle();
        args.putSerializable("trip", trip);
        eventListFrag.setArguments(args);
        eventViewerFrag = new EventViewerFrag();
        createEventFrag = new CreateEventFrag();

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.EventLayout, eventListFrag, "LIST_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onStop(){
        super.onStop();

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

    public void onBackPressed()
    {
        FragmentManager fm = this.getFragmentManager();
        if(fm.getBackStackEntryCount() == 1) {
            super.onBackPressed();
            Log.d("EventActivity", "superback");
        }
        else {
            fm.popBackStack();
            Log.d("Event Activity", "pop back stack");
        }
    }


    public void submitEvent(View view){
        Event event;


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        EditText locationEdit = (EditText) findViewById(R.id.locationText);
        String location = locationEdit.getText().toString();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        Date date = CreateSequentialFile.getDateFromDatePicker(datePicker);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        String time = timePicker.getCurrentHour().toString() +":" + timePicker.getCurrentMinute().toString();

        EditText infoEdit = (EditText) findViewById(R.id.infoText);
        String info = infoEdit.getText().toString();

        if (spinner.getSelectedItem().toString().equals("Dining")) {
            event = new Food(date, location, time, info);
        } else if (spinner.getSelectedItem().toString().equals("Lodging")) {
            event = new Lodge(date, date, location, time, info);

        } else if (spinner.getSelectedItem().toString().equals("Leisure")) {
            event = new Fun(date, location, time, info);

        } else if (spinner.getSelectedItem().toString().equals("Travel")) {
            if(spinner.getSelectedItem().toString().equals("Train")){

            }
            event = new Train(date, "Train", "now", "dumb", 5, "this trip rocks");

        } else {
            event = new Train();
        }

        if(newEvent){
            trip.addEvent(event);
        }else{
            trip.removeEvent(currentEvent);
            trip.addEvent(event);
        }
        fragmentTransaction = getFragmentManager().beginTransaction();
        eventListFrag = new EventListFrag();
        Bundle args = new Bundle();
        args.putSerializable("trip", trip);
        eventListFrag.setArguments(args);

        CreateSequentialFile db = new CreateSequentialFile();
        db.openReadFile(this);
        List<Trip> trips;
        trips = db.loadTrips();
        db.closeReadFile();
        for(Trip t : trips){
            if (t.getId().equals(trip.getId())){
                Log.d("event saving", "in the if");
                trips.remove(t);
                trips.add(trip);
                break;
            }
        }
        db.openWriteFile(this);
        for(int i = 0; i < trips.size(); i++){
            db.saveTrip(trips.get(i));
        }
        db.closeWriteFile();

        fragmentTransaction.replace(R.id.EventLayout, eventListFrag, "LIST_EVENT");
        getFragmentManager().popBackStack();
        if(!newEvent){
            getFragmentManager().popBackStack();
        }
        fragmentTransaction.commit();
    }

    public void newEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        createEventFrag = new CreateEventFrag();
        Bundle args = new Bundle();
        newEvent = true;

        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void editEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        createEventFrag = new CreateEventFrag();
        Bundle args = new Bundle();
        newEvent = false;
        //something something on fragment interaction
        //args.putSerializable("event", event);

        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void onFragmentInteraction(Event event){
        currentEvent = event;
        Bundle args = new Bundle();
        args.putSerializable("event", event);
        eventViewerFrag = new EventViewerFrag();
        eventViewerFrag.setArguments(args);

        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.EventLayout, eventViewerFrag, "VIEW_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onFragmentInteraction(String eventType){

        DatePicker datePicker = (DatePicker) findViewById(R.id.endPicker);
        TextView textView = (TextView) findViewById(R.id.endDatePickerText);
        Spinner travelSpinner = (Spinner) findViewById(R.id.travelSpinner);

        travelSpinner.setVisibility(View.GONE);
        if(eventType.equals("Lodging")){
            datePicker.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }else if(eventType.equals("Travel")){
            travelSpinner.setVisibility(View.VISIBLE);
        }
    }

    public void onFragmentInteraction(Uri uri){}
}
