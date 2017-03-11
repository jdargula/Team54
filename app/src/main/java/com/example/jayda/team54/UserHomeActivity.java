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


public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserWelcome;
    private Button buttonLogout;
    private Button buttonEditProfile;
    private Button buttonViewWaterReports;
    private Button buttonViewAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserWelcome = (TextView) findViewById(R.id.textViewUserWelcome);
        textViewUserWelcome.setText("Welcome, " + user.getEmail()); //We can change this to display name later
        buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);
        buttonViewWaterReports = (Button) findViewById(R.id.buttonViewWaterReports);
        buttonViewAvailability = (Button) findViewById(R.id.buttonViewAvailability);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonEditProfile.setOnClickListener(this);
        buttonViewWaterReports.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonViewAvailability.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonEditProfile) {
            finish();
            startActivity(new Intent(this, ApplicationActivity.class));
        }

        if(view == buttonViewWaterReports){
            finish();
            startActivity(new Intent(this, ViewReportsActivity.class));
        }

        if (view == buttonViewAvailability){
            finish();
            startActivity(new Intent(this, DisplayMap.class));
        }

        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}