package com.example.jayda.team54;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Alex on 2/21/2017.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserWelcome;
    private Button buttonLogout;
    private Button buttonEditProfile;
    private Button buttonSubmitWaterReport;
    private Button buttonViewWaterReports;
    private Button buttonViewAvailability;
    private Button buttonSubmitWaterPurityReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserWelcome = (TextView) findViewById(R.id.textViewUserWelcome);
        textViewUserWelcome.setText("Welcome, " + user.getEmail()); //We can change this to display name later
        buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);
        buttonSubmitWaterReport = (Button) findViewById(R.id.buttonSubmitWaterReport);
        buttonViewWaterReports = (Button) findViewById(R.id.buttonViewWaterReports);
        buttonViewAvailability = (Button) findViewById(R.id.buttonViewAvailability);
        buttonSubmitWaterPurityReport = (Button) findViewById(R.id.buttonSubmitWaterPurityReport);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonEditProfile.setOnClickListener(this);
        buttonSubmitWaterReport.setOnClickListener(this);
        buttonViewWaterReports.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonViewAvailability.setOnClickListener(this);
        buttonSubmitWaterPurityReport.setOnClickListener(this);
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

        if (view == buttonViewAvailability){
            finish();
            startActivity(new Intent(this, DisplayMap.class));
        }

        if (view == buttonSubmitWaterPurityReport){
            finish();
            startActivity(new Intent(this, SubmitPurityActivity.class));
        }

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
