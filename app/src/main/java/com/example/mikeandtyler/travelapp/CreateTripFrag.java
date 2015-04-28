package com.example.mikeandtyler.travelapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateTripFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateTripFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTripFrag extends Fragment {


    EditText tripNameText;
    Boolean newTrip;



    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTrip.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTripFrag newInstance(String param1, String param2) {
        CreateTripFrag fragment = new CreateTripFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CreateTripFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newTrip = true;
        if (getArguments() != null) {
            //if we are editing an existing trip we are expecting false, otherwise it should always be true
            newTrip = getArguments().getBoolean("newTrip", true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_trip, container, false);
        tripNameText = (EditText) view.findViewById(R.id.editText);
        tripNameText.setHint("Name your Trip!");

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    public void onStart(){
        super.onStart();
        //if we are editing a trip, have the activity populate our fields
        if(!newTrip){
            mListener.fillTrip();
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
        //Unused interaction function
        public void onFragmentInteraction(Uri uri);
        //helper function to populate fragment with existing trip info
        public void fillTrip();
    }

}
