package com.example.jayda.team54;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PurityDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //extra info passed through Intent by ViewPurityActivity

    //ui elements
    private TextView reportNumber;
    private TextView dateTime;
    private TextView reporterName;
    private TextView waterLocation;
    private TextView waterCondition;
    private TextView virusPPM;
    private TextView contaminantPPM;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purity_detail);

        String key = getIntent().getStringExtra("key");

        //get ui elements
        reportNumber = (TextView) findViewById(R.id.reportNum);
        dateTime = (TextView) findViewById(R.id.dateTime);
        reporterName = (TextView) findViewById(R.id.reporterName);
        waterLocation = (TextView) findViewById(R.id.waterLocation);
        waterCondition = (TextView) findViewById(R.id.waterCondition);
        virusPPM = (TextView) findViewById(R.id.virusPPM);
        contaminantPPM = (TextView) findViewById(R.id.contaminantPPM);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(this);

        //read report info from database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reportRef = databaseRef.child("purity").child(key);
        ValueEventListener reportListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap report = (HashMap) dataSnapshot.getValue();
                String dateValue = report.get("dateTime").toString();
                String numValue = report.get("reportNum").toString();
                numValue = "Report #" + numValue;
                String nameValue = report.get("workerName").toString();
                String conditionValue = report.get("waterCondition").toString();
                String locationValue = report.get("waterLocation").toString();
                String virusValue = report.get("virusPPM").toString();
                String contaminantValue = report.get("contaminantPPM").toString();
                dateTime.setText(dateValue);
                reportNumber.setText(numValue);
                reporterName.setText(nameValue);
                waterCondition.setText(conditionValue);
                waterLocation.setText(locationValue);
                virusPPM.setText(virusValue);
                contaminantPPM.setText(contaminantValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reportRef.addValueEventListener(reportListener);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonBack) {
            finish();
            startActivity(new Intent(this, ViewPurityActivity.class));
        }
    }
}
