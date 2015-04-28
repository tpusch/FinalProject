package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventListFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFrag extends Fragment implements AbsListView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    Trip trip;
    List<Event> events;

    private ArrayAdapter mAdapter;
    private AbsListView mListView;
    AlertDialog alertDialog;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFrag.
     */
    public static EventListFrag newInstance(String param1, String param2) {
        EventListFrag fragment = new EventListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = new ArrayList<>();
        if (getArguments() != null) {
            trip = (Trip) getArguments().getSerializable("trip");
            if(trip.getEvents() != null && trip.getEvents().size() > 0) {
                events = trip.getEvents();

                Collections.sort(events, new Comparator<Event>(){
                    public int compare(Event o1, Event o2) {
                        if (o1.getDate() == null || o2.getDate() == null)
                            return 0;
                        return o1.getDate().compareTo(o2.getDate());

                    }
                });

            }
        }

        mAdapter = new ArrayAdapter<Event>(getActivity(), android.R.layout.simple_selectable_list_item, events);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedState){
        super.onActivityCreated(savedState);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Creates the alert dialog and buttons for a long click on an event
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Would you like to delete this event?");
                //yes button for deleting an event
                alertDialog.setButton2("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.removeEvent((Event) parent.getItemAtPosition(position));
                        //trip.removeEvent((Event) parent.getItemAtPosition(position));
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Event Removed", Toast.LENGTH_LONG).show();
                    }
                });
                //No button for deleting an event
                alertDialog.setButton("NO", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    //Listener for the selection of an event in our Event List
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onFragmentInteraction(events.get(position));
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
        //An event has been selected
        public void onFragmentInteraction(Event event);
        //Long click has called an event to be deleted
        public void removeEvent(Event event);
    }

}
