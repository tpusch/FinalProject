package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFrag extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner eventSpinner, travelSpinner;
    Boolean newEvent;
    Event event;

    ArrayAdapter<CharSequence> adapter, adapter1;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFrag newInstance(String param1, String param2) {
        CreateEventFrag fragment = new CreateEventFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateEventFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newEvent = true;
        //loading based on if we are editing an event or creating a new one
        if (getArguments() != null) {
            newEvent = getArguments().getBoolean("newEvent",true);
            if(!newEvent){
                event = (Event) getArguments().getSerializable("event");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        //create event and travel spinners for use in our fragment

        eventSpinner = (Spinner) view.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.eventTypes, android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(adapter);
        eventSpinner.setOnItemSelectedListener(this);

        travelSpinner = (Spinner) view.findViewById(R.id.travelSpinner);
        adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.travelTypes, android.R.layout.simple_spinner_dropdown_item);
        travelSpinner.setAdapter(adapter1);
        travelSpinner.setOnItemSelectedListener(this);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(eventSpinner.getSelectedItem().toString());
        }
    }

    //mandatory nothing selected from spinner
    public void onNothingSelected(AdapterView<?> parent) {
        
    }

    //listener for spinner dropdown selections
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //spinner selection is event spinner: notify activity
        if(parent.getAdapter().equals(adapter)){
            mListener.onFragmentInteraction(parent.getSelectedItem().toString());
        //spinner selection is travel spinner: notify activity
        }else if(parent.getAdapter().equals(adapter1)){
            mListener.onTravelSpinnerInteraction(parent.getSelectedItem().toString());
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

    public void onStart(){
        super.onStart();
        //Call for activity to populate fields if we are editing an existing event
        if(!newEvent){
            mListener.loadEventItems();
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
        //Event spinner dropdown interaction
        public void onFragmentInteraction(String event);
        //Travel spinner dropdown interaction
        public void onTravelSpinnerInteraction(String event);
        //Helper function for activity to populate existing fields
        public void loadEventItems();
    }

}
