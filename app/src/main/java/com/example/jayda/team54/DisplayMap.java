package com.example.jayda.team54;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DisplayMap extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private DatabaseReference ref;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_map);
        ref = FirebaseDatabase.getInstance().getReference();
        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Method to retrieve locations from database
     */
    private void getData() {
        // retrieve data from database
        DatabaseReference valueReference = ref.child("reports");
        ValueEventListener valueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){   //for 1, 2, ..., n
                    try {
                        String reportLocation = snapshot.child("waterLocation").getValue().toString();
                        String[] locArr = reportLocation.split(",");
                        int lat = Integer.parseInt(locArr[0].trim());
                        int lng = Integer.parseInt(locArr[1].trim());
                        LatLng tempLoc = new LatLng(lat,lng);

                        String numStr = snapshot.child("reportNum").getValue().toString();
                        String typeStr = snapshot.child("waterType").getValue().toString();
                        String conditionStr = snapshot.child("waterCondition").getValue().toString();
                        mMap.addMarker(new MarkerOptions().position(tempLoc).title("Report #" + numStr).snippet(typeStr + ", " + conditionStr));
                    } catch (Exception e){
                        //
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        valueReference.addValueEventListener(valueListener);
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
        UiSettings mapUI = mMap.getUiSettings();
        mapUI.setZoomControlsEnabled(true);
        this.getData();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                return false;
            }
        });

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onClick(View view){
        if (view == buttonBack){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
