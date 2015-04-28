package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TripListFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TripListFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripListFrag extends Fragment implements AbsListView.OnItemClickListener {

    private ArrayAdapter mAdapter;
    private AbsListView mListView;
    AlertDialog alertDialog;

    private OnFragmentInteractionListener mListener;

    List<Trip> trips;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityListFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static TripListFrag newInstance(String param1, String param2) {
        TripListFrag fragment = new TripListFrag();

        return fragment;
    }

    public TripListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trips = new ArrayList<>();
        if (getArguments() != null) {
            int size = getArguments().getInt("size");
            //Loads the trips into the trip list arraylist
            for(int i = 0; i < size; i++){
                Trip trip = (Trip) getArguments().getSerializable("trip"+i);
                trips.add(trip);
            }
        }
        mAdapter = new ArrayAdapter<Trip>(getActivity(), android.R.layout.simple_list_item_1, trips);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

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
                //should give an alert option

                //Building the long click alert dialog and buttons
                alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Would you like to delete this trip?");
                //Yes Button for deleting a trip
                alertDialog.setButton2("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.removeTrip((Trip) parent.getItemAtPosition(position));
                        trips.remove(parent.getItemAtPosition(position));
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Trip Removed", Toast.LENGTH_LONG).show();
                    }
                });
                //No Button for deleting a trip
                alertDialog.setButton("NO", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                //Edit Button for editing a trip
                alertDialog.setButton3("EDIT", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        mListener.editTrip((Trip) parent.getItemAtPosition(position));
                    }
                });
                alertDialog.show();

//                mListener.removeTrip((Trip) parent.getItemAtPosition(position));
//                trips.remove(parent.getItemAtPosition(position));
//                mAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int i) {
        if (mListener != null) {
            mListener.onTripFragmentInteraction(trips.get(i));
        }
    }

    //Item selected out of the trip list, sends that information to the activity
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onTripFragmentInteraction(trips.get(position));
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
        // TODO: Update argument type and name
        //a trip has been selected out of the list
        public void onTripFragmentInteraction(Trip trip);
        //long press has selected to delete a trip
        public void removeTrip(Trip trip);
        //long press has selected to edit a trip
        public void editTrip(Trip trip);
    }

}
