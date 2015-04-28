package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventViewerFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventViewerFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventViewerFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView nameText, typeText, locationText, dateText, timeText, infoText, endDateText, travelText, destText;

    // TODO: Rename and change types of parameters
    Event event;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventViewer.
     */
    // TODO: Rename and change types and number of parameters
    public static EventViewerFrag newInstance(String param1, String param2) {
        EventViewerFrag fragment = new EventViewerFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventViewerFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //pull out the event argument to be viewed
            event = (Event) getArguments().getSerializable("event");
        }
    }

    //Populates all fields with event information
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_viewer, container, false);
        nameText = (TextView) view.findViewById(R.id.nameText);
        typeText = (TextView) view.findViewById(R.id.typeText);
        locationText = (TextView) view.findViewById(R.id.locationText);
        dateText = (TextView) view.findViewById(R.id.dateText);
        timeText = (TextView) view.findViewById(R.id.durationText);
        infoText = (TextView) view.findViewById(R.id.infoText);
        endDateText = (TextView) view.findViewById(R.id.endDateText);
        travelText = (TextView) view.findViewById(R.id.travelNumberText);
        destText = (TextView) view.findViewById(R.id.destinationText);

        nameText.setText("Event Details");
        typeText.setText(event.getType());
        locationText.setText(event.getLocation());
        dateText.setText(event.getDate().toString().substring(0,10));
        infoText.setText(event.getInfo());

        String eventType = event.getType();

        //Dynamically populates specific fields based on event type
        if(eventType.equals("Lodging")){
            dateText.setText("Check In Date: " + event.getDate().toString().substring(0,10));
            endDateText.setVisibility(View.VISIBLE);
            endDateText.setText("Check Out Date: " + ((Lodge) event).getEndDate().toString().substring(0,10) );
            timeText.setText("Check In Time: " + event.getTime());
        }else if(eventType.equals("Leisure")) {
            endDateText.setVisibility(View.GONE);
            timeText.setVisibility(View.VISIBLE);
            dateText.setText("Event Date: " + event.getDate().toString().substring(0,10));
            timeText.setText("Event time: " + event.getTime());
        }else if(eventType.equals("Travel")) {
            String travelType = ((Travel) event).getTravelType();
            typeText.setText("Travel - " + travelType);
            dateText.setText("Departure Date: " + event.getDate().toString().substring(0,10));
            timeText.setText("Departure time: " + event.getTime());
            destText.setVisibility(View.VISIBLE);
            locationText.setText("From: " + event.getLocation());
            destText.setText("To: " + ((Travel) event).getToCity());

            //further populates based on specific subtypes
            if (travelType.equals("Flight")) {
                travelText.setVisibility(View.VISIBLE);
                travelText.setText("Flight Number: " + ((Flight) event).getFlightNumber());

            } else if (travelType.equals("Car")) {

            } else if (travelType.equals("Train")) {
                travelText.setVisibility(View.VISIBLE);
                travelText.setText("Train Number: " + ((Train) event).getTrainNumber());
            }
        }else if(eventType.equals("Dining")){
            dateText.setText("Reservation date: " + event.getDate().toString().substring(0,10));
            timeText.setText("Reservation time: " + event.getTime());
        }

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        //unused fragment interaction
        public void onFragmentInteraction(Uri uri);
    }

}
