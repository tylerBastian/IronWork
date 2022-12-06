package com.cmpsc475.ironwork;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private List<Object> viewItems = new ArrayList<>();
    private List<Job> jobs = new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView locationTextView;
    LocationManager lm;
    private Location gps_loc;
    private Location network_loc;
    private Location final_loc;
    private double longitude;
    private double latitude;
    private String userCountry, userAddress;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recentRunsList);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new JobListAdapter(getContext(), viewItems);
        mRecyclerView.setAdapter(mAdapter);

        getActivity().setTitle("All Jobs");


        locationTextView = view.findViewById(R.id.job_list_location);
        lm = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        Log.d("SearchFragment", "onCreateView: " + MainActivity.getJobViewModel().getAllJobs());
        addJobsFromDatabase();

        getUserLocation();




        return view;
    }


//    private void sendInterestedEmail(Job job) {
//        String email = job.getJobContactEmail();
//        String subject = "Interested in Job";
//        String message = "I am interested in the job " + job.getJobTitle() + " at " + job.getJobLocation() + ". Please contact me to discuss further.";
//
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{job.getJobContactEmail()});
//        i.putExtra(Intent.EXTRA_SUBJECT, "Interested in Job");
//        i.putExtra(Intent.EXTRA_TEXT   , "I am interested in the job " + job.getJobTitle() + " at " + job.getJobLocation() + ". Please contact me to discuss further.");
//        try {
//            startActivity(Intent.createChooser(i, "Send mail..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//        }
//
//    }

    private void addJobsFromDatabase() {

        viewItems.addAll((List<Job>) MainActivity.getJobViewModel().getAllJobs());

    }

    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        try {

            gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Log.d("GPS", "GPS: " + gps_loc);

            if (gps_loc != null) {
                final_loc = gps_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else if (network_loc != null) {
                final_loc = network_loc;
                latitude = final_loc.getLatitude();
                longitude = final_loc.getLongitude();
            } else {
                latitude = 0.0;
                longitude = 0.0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Log.d("Address", addresses.toString());
            if (addresses != null && addresses.size() > 0) {
                userCountry = addresses.get(0).getCountryName();
                userAddress = addresses.get(0).getAddressLine(0);
                Log.d("Location", "Country: " + userCountry + " Address: " + userAddress);
                locationTextView.setText("Jobs closest to: "+userCountry + ", " + userAddress);
            }
            else {
                userCountry = "Unknown";
                locationTextView.setText(userCountry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}