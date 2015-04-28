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
import java.util.Calendar;
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

        eventViewerFrag = new EventViewerFrag();
        createEventFrag = new CreateEventFrag();

        //immediately start the Event List frag to display our events
        eventListFrag = new EventListFrag();
        trip = (Trip) getIntent().getSerializableExtra("trip");
        Bundle args = new Bundle();
        args.putSerializable("trip", trip);
        eventListFrag.setArguments(args);

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

    //handles the back button through fragments or to previous activity
    public void onBackPressed()
    {
        FragmentManager fm = this.getFragmentManager();
        if(fm.getBackStackEntryCount() == 1) {
            super.onBackPressed();
        }
        else {
            fm.popBackStack();
        }
    }

    //onClick function: Submit from CreateEventFrag
    public void submitEvent(View view){
        Event event;


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner travelSpinner = (Spinner) findViewById(R.id.travelSpinner);
        EditText locationEdit = (EditText) findViewById(R.id.locationText);
        String location = locationEdit.getText().toString();

        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        Date date = CreateSequentialFile.getDateFromDatePicker(datePicker);

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        date.setHours(timePicker.getCurrentHour());
        date.setMinutes(timePicker.getCurrentMinute());
        String time;
        if (timePicker.getCurrentMinute() < 10) {
            time = timePicker.getCurrentHour().toString() + ":0" + timePicker.getCurrentMinute().toString();
        }
        else{
            time = timePicker.getCurrentHour().toString() + ":" + timePicker.getCurrentMinute().toString();
        }

        EditText infoEdit = (EditText) findViewById(R.id.infoText);
        String info = infoEdit.getText().toString();

        if (spinner.getSelectedItem().toString().equals("Dining")) {
            event = new Food(date, location, time, info);
        } else if (spinner.getSelectedItem().toString().equals("Lodging")) {
            DatePicker endDatePicker = (DatePicker) findViewById(R.id.endPicker);
            Date endDate = CreateSequentialFile.getDateFromDatePicker(endDatePicker);

            event = new Lodge(date, endDate, location, time, info);

        } else if (spinner.getSelectedItem().toString().equals("Leisure")) {
            event = new Fun(date, location, time, info);

        } else{
            EditText destinationEdit = (EditText) findViewById(R.id.endLocation);
            String destination = destinationEdit.getText().toString();

            EditText numberEdit = (EditText) findViewById(R.id.travelNumber);
            String travelNumber = numberEdit.getText().toString();

            if(travelSpinner.getSelectedItem().toString().equals("Train")){
                event = new Train(date, location, destination, time, travelNumber, info);
            }else if(travelSpinner.getSelectedItem().toString().equals("Flight")){
                event = new Flight(date, location, destination, time, travelNumber, info);
            }else/*car*/{
                event = new Car(date, location, destination, time, info);
            }
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

        //Saves new events to prevent data loss on crash
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

    //onClick function: New Event from EventListFrag
    public void newEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        createEventFrag = new CreateEventFrag();
        Bundle args = new Bundle();
        newEvent = true;

        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //onClick function: Edit Event from EventViewerFrag
    public void editEvent(View view){
        fragmentTransaction = getFragmentManager().beginTransaction();
        createEventFrag = new CreateEventFrag();
        Bundle args = new Bundle();
        newEvent = false;
        args.putBoolean("newEvent", newEvent);
        createEventFrag.setArguments(args);
        fragmentTransaction.replace(R.id.EventLayout, createEventFrag, "CREATE_EVENT");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Fragment Interaction from EventListFrag
    //Views the selected event from the event list
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

    //Fragment Interaction from CreateEventFrag
    //Dynamically populates the CreateEventFrag based on the selected event drop-down item
    public void onFragmentInteraction(String eventType){

        DatePicker datePicker = (DatePicker) findViewById(R.id.endPicker);
        TextView endDatePicker = (TextView) findViewById(R.id.endDatePickerText);
        TextView startDatePicker = (TextView) findViewById(R.id.startDatePickerText);
        TextView startTime = (TextView) findViewById(R.id.setStartTime);
        Spinner travelSpinner = (Spinner) findViewById(R.id.travelSpinner);
        EditText endLocation = (EditText) findViewById(R.id.endLocation);
        EditText number = (EditText) findViewById(R.id.travelNumber);


        //series displaying and populating fields based on the events type
        if(eventType.equals("Lodging")){
            datePicker.setVisibility(View.VISIBLE);
            endDatePicker.setVisibility(View.VISIBLE);
            travelSpinner.setVisibility(View.GONE);
            endLocation.setVisibility(View.GONE);
            number.setVisibility(View.GONE);
            startDatePicker.setText("Check-In Date");
            endDatePicker.setText("Check-Out Date");
            startTime.setText("Check-In Time");
        }else if(eventType.equals("Travel")){
            travelSpinner.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.GONE);
            endDatePicker.setVisibility(View.GONE);
            endLocation.setVisibility(View.VISIBLE);
            startDatePicker.setText("Departure Date");
            startTime.setText("Departure Time");
            number.setVisibility(View.VISIBLE);
        }else if(eventType.equals("Dining")){
            travelSpinner.setVisibility(View.GONE);
            endDatePicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            endLocation.setVisibility(View.GONE);
            number.setVisibility(View.GONE);
            startDatePicker.setText("Reservation Date");
            startTime.setText("Reservation Time");
        }else if(eventType.equals("Leisure")){
            travelSpinner.setVisibility(View.GONE);
            endDatePicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            endLocation.setVisibility(View.GONE);
            number.setVisibility(View.GONE);
            startDatePicker.setText("Event Date");
            startTime.setText("Event Time");

        }

    }


    //FragmentInteraction from CreateEventFrag
    //Further dynamic population based on the second dropdown travel spinner
    public void onTravelSpinnerInteraction(String eventType){

        EditText number = (EditText) findViewById(R.id.travelNumber);

        if(eventType.equals("Train")){
            number.setVisibility(View.VISIBLE);
            number.setHint("Train Number");
        }else if(eventType.equals("Flight")){
            number.setVisibility(View.VISIBLE);
            number.setHint("Flight Number");

        }else if(eventType.equals("Car")){
            number.setVisibility(View.GONE);
        }
    }

    public void removeEvent(Event event) {
        trip.removeEvent(event);

        //Saves our most recent changes
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
    }

    //Fragment Interaction from CreateEventFrag
    //Populates all fields based on the current event when editing an existing event
    public void loadEventItems(){
        DatePicker startDatePicker = (DatePicker) findViewById(R.id.datePicker);
        DatePicker endDatePicker = (DatePicker) findViewById(R.id.endPicker);
        Spinner travelSpinner = (Spinner) findViewById(R.id.travelSpinner);
        Spinner eventSpinner = (Spinner) findViewById(R.id.spinner);
        EditText endLocation = (EditText) findViewById(R.id.endLocation);
        EditText number = (EditText) findViewById(R.id.travelNumber);
        EditText location = (EditText) findViewById(R.id.locationText);
        EditText info = (EditText) findViewById(R.id.infoText);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentEvent.getDate());
        startDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        info.setText(currentEvent.getInfo());
        location.setText(currentEvent.getLocation());

        String[] timeArray = currentEvent.getTime().split(":");
        timePicker.setCurrentMinute(Integer.parseInt(timeArray[1]));
        timePicker.setCurrentHour(Integer.parseInt(timeArray[0]));


        String eventType = currentEvent.getType();
        if(eventType.equals("Dining")){
            eventSpinner.setSelection(0);

        }else if(eventType.equals("Leisure")){
            eventSpinner.setSelection(1);
        }else if(eventType.equals("Lodging")){
            eventSpinner.setSelection(2);
            Date d = ((Lodge)currentEvent).getEndDate();
            cal.setTime(d);
            endDatePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }else if(eventType.equals("Travel")){
            eventSpinner.setSelection(3);
            String travelType = ((Travel) currentEvent).getTravelType();
            endLocation.setText(((Travel) currentEvent).getToCity());
            if(travelType.equals("Flight")){
                travelSpinner.setSelection(0);
                number.setText(((Flight)currentEvent).getFlightNumber());
            }else if(travelType.equals("Train")){
                travelSpinner.setSelection(1);
                number.setText(((Train)currentEvent).getTrainNumber());
            }else if(travelType.equals("Car")){
                travelSpinner.setSelection(2);
            }
        }
    }

    //unused fragment interaction
    public void onFragmentInteraction(Uri uri){}
}
