package com.example.mikeandtyler.travelapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateSequentialFile file = new CreateSequentialFile();
        file.openWriteFile();
        Date d = new Date();
        Event event = new Food(d, "string", 2.2f);
        List<Event> events = new ArrayList<>();
        events.add(event);
        Trip trip = new Trip(events, d, d, "first Trip");
        file.saveTrip(trip);
        file.closeWriteFile();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void go(View view){
        Intent i = new Intent(this, TripActivity.class);
        startActivity(i);
    }
}
