package com.example.jayda.team54;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.fitness.data.Value;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Alex on 2/21/2017.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    private TextView textViewUserWelcome;
    private Button buttonLogout;
    private Button buttonEditProfile;
    private Button buttonSubmitWaterReport;
    private Button buttonViewWaterReports;
    private Button buttonViewAvailability;
    private Button buttonViewPurity;
    private Button buttonSubmitWaterPurityReport;
    private Button buttonViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //get ui references
        textViewUserWelcome = (TextView) findViewById(R.id.textViewUserWelcome);
        textViewUserWelcome.setText("Welcome, " + user.getEmail()); //We can change this to display name later
        buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);
        buttonSubmitWaterReport = (Button) findViewById(R.id.buttonSubmitWaterReport);
        buttonViewWaterReports = (Button) findViewById(R.id.buttonViewWaterReports);
        buttonViewAvailability = (Button) findViewById(R.id.buttonViewAvailability);
        buttonViewPurity = (Button) findViewById(R.id.buttonViewPurity);
        buttonSubmitWaterPurityReport = (Button) findViewById(R.id.buttonSubmitWaterPurityReport);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonViewHistory = (Button) findViewById(R.id.buttonViewHistory);

        //set up buttons
        buttonEditProfile.setOnClickListener(this);
        buttonSubmitWaterReport.setOnClickListener(this);
        buttonViewWaterReports.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonViewAvailability.setOnClickListener(this);
        buttonViewPurity.setOnClickListener(this);
        buttonSubmitWaterPurityReport.setOnClickListener(this);
        buttonViewHistory.setOnClickListener(this);

        //if current user is of type user, hide certain buttons
        DatabaseReference userReference = databaseReference.child(user.getUid());
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("account").getValue().toString().equals("User")){
                    //hide button(s)
                    buttonSubmitWaterPurityReport.setVisibility(View.GONE);
                }
                if (!(dataSnapshot.child("account").getValue().toString().equals("Manager"))) {
                    buttonViewPurity.setVisibility(View.GONE);
                    buttonViewHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        userReference.addValueEventListener(userListener);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonEditProfile) {
            finish();
            startActivity(new Intent(this, ApplicationActivity.class));
        }

        if(view == buttonSubmitWaterReport){
            finish();
            startActivity(new Intent(this, SubmitReportActivity.class));
        }

        if(view == buttonViewWaterReports){
            finish();
            startActivity(new Intent(this, ViewReportsActivity.class));
        }

        if (view == buttonViewPurity) {
            finish();
            startActivity(new Intent(this, ViewPurityActivity.class));
        }

        if (view == buttonViewAvailability){
            finish();
            startActivity(new Intent(this, DisplayMap.class));
        }

        if (view == buttonSubmitWaterPurityReport){
            finish();
            startActivity(new Intent(this, SubmitPurityActivity.class));
        }

        if (view == buttonViewHistory){
            finish();
            startActivity(new Intent(this, HistoryActivity.class));
        }

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
