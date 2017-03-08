package com.example.jayda.team54;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DisplayMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private List<String> listArr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Method to retrieve locations from database
     */
    private void getData() {
        ref = database.getReference();

        // retrieve data from database
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                listArr.clear();
                for (DataSnapshot snapshot : data.child("reports").getChildren()) {
                    String reportLocation = snapshot.child("waterLocation").getValue(String.class);
                    listArr.add(reportLocation);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Could not read");
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // populate the ArrayList of Water Reports
        this.getData();
        for (String reportsLoc : listArr) {
            try {
                String[] latLong = reportsLoc.split(",");
                int lat = Integer.parseInt(latLong[0]);
                int lng = Integer.parseInt(latLong[1]);
                LatLng tempLocation = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(tempLocation));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("The location was not entered in the form latitude, longitude");
            }
        }
        // example marker code
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
