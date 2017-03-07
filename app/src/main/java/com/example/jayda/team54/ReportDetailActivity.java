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

public class ReportDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //extra info passed through Intent by ViewReportsActivity
    private String key;

    //ui elements
    private TextView reportNumber;
    private TextView dateTime;
    private TextView reporterName;
    private TextView waterLocation;
    private TextView waterType;
    private TextView waterCondition;
    private Button buttonBack;

    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        key = getIntent().getStringExtra("key");

        //get ui elements
        reportNumber = (TextView) findViewById(R.id.reportNum);
        dateTime = (TextView) findViewById(R.id.dateTime);
        reporterName = (TextView) findViewById(R.id.reporterName);
        waterLocation = (TextView) findViewById(R.id.waterLocation);
        waterType = (TextView) findViewById(R.id.waterType);
        waterCondition = (TextView) findViewById(R.id.waterCondition);
        buttonBack = (Button) findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(this);

        //read report info from database
        databaseRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reportRef = databaseRef.child("reports").child(key);
        ValueEventListener reportListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap report = (HashMap) dataSnapshot.getValue();
                String dateValue = report.get("dateTime").toString();
                String numValue = report.get("reportNum").toString();
                numValue = "Report #" + numValue;
                String nameValue = report.get("reporterName").toString();
                String conditionValue = report.get("waterCondition").toString();
                String typeValue = report.get("waterType").toString();
                String locationValue = report.get("waterLocation").toString();
                dateTime.setText(dateValue);
                reportNumber.setText(numValue);
                reporterName.setText(nameValue);
                waterCondition.setText(conditionValue);
                waterType.setText(typeValue);
                waterLocation.setText(locationValue);
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
            startActivity(new Intent(this, ViewReportsActivity.class));
        }
    }
}
