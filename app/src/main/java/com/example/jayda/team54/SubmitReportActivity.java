package com.example.jayda.team54;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity for submitting a water report.
 */

public class SubmitReportActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSubmitReport;
    private Button buttonCancel;
    private EditText editTextDateTime;
    private EditText editTextReportNumber;
    private EditText editTextNameReporter;
    private EditText editTextLocationWater;
    private Spinner spinnerTypeWater;
    private Spinner spinnerConditionWater;

    private long reportNumberValue;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //find references to ui elements
        buttonSubmitReport = (Button) findViewById(R.id.buttonSubmitReport);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        editTextDateTime = (EditText) findViewById(R.id.editTextDateTime);
        editTextReportNumber = (EditText) findViewById(R.id.editTextReportNumber);
        editTextNameReporter = (EditText) findViewById(R.id.editTextNameReporter);
        editTextLocationWater = (EditText) findViewById(R.id.editTextLocationWater);
        spinnerTypeWater = (Spinner) findViewById(R.id.spinnerTypeWater);
        spinnerConditionWater = (Spinner) findViewById(R.id.spinnerConditionWater);

        //Autogenerate report number
        DatabaseReference reportNumReference = databaseReference.child("reports");
        ValueEventListener reportNumListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("reportNum").getValue() == null){
                    reportNumberValue = 0;
                    reportNumberValue++;
                    String reportNumberStr = reportNumberValue + "";
                    editTextReportNumber.setText(reportNumberStr);
                } else{
                    //read from database into local var
                    //increment that local var and use it to set the ui value
                    //only write back to the database after the user has clicked submit and everything passes
                    //aka right before you return to home
                    reportNumberValue = (long) dataSnapshot.child("reportNum").getValue();
                    reportNumberValue++;
                    String reportNumStr = reportNumberValue + "";
                    editTextReportNumber.setText(reportNumStr);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reportNumReference.addValueEventListener(reportNumListener);

        //Autogenerate date and time
        TimeZone tz = TimeZone.getTimeZone("America/New_York");
        Calendar calendar = new GregorianCalendar(tz);
        Date currentTime = new Date();
        calendar.setTime(currentTime);
        String currentDateTime = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":"
                + calendar.get(Calendar.MINUTE);
        editTextDateTime.setText(currentDateTime);

        //Autogenerate name of reporter
        DatabaseReference nameReference = databaseReference.child(user.getUid()).child("name");
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                editTextNameReporter.setText(name, TextView.BufferType.EDITABLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nameReference.addValueEventListener(nameListener);

        //Set up spinners
        String[] typeWaterArr = {"Bottled", "Well", "Stream", "Lake", "Spring", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeWaterArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeWater.setAdapter(adapter);

        String[] conditionWaterArr = {"Waste", "Treatable-Clear", "Treatable-Muddy", "Potable"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, conditionWaterArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConditionWater.setAdapter(adapter1);

        //Set up buttons
        buttonSubmitReport.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    /**
     * Pulls information from the UI elements, creates a WaterSourceReport instance using that,
     * and then stores that into the database.
     */
    private void submitWaterReport(){
        //pull info from ui elements
        //btw a lot of these will prob change types
        String dateTime = editTextDateTime.getText().toString().trim();
        String reportNumStr = editTextReportNumber.getText().toString().trim();
        int reportNum = Integer.parseInt(reportNumStr);
        String nameReporter = editTextNameReporter.getText().toString().trim();
        String locationWater = editTextLocationWater.getText().toString().trim();
        String typeWater = spinnerTypeWater.getSelectedItem().toString();
        String conditionWater = spinnerConditionWater.getSelectedItem().toString();

        //input validation for location
        String[] splitStr = locationWater.split(",");
        try {
            int lat = Integer.parseInt(splitStr[0].trim());
            int lng = Integer.parseInt(splitStr[1].trim());
            if (lat < -90 || lat > 90){
                Toast.makeText(SubmitReportActivity.this, "Latitude is from -90 to 90", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lng < -180 || lng > 180) {
                Toast.makeText(SubmitReportActivity.this, "Longitude is from -180 to 180", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e){
            Toast.makeText(SubmitReportActivity.this, "Please enter location format: LAT,LONG", Toast.LENGTH_SHORT).show();
            return;
        }

        //enter info into a WaterSourceReport instance
        WaterSourceReport waterReportInstance = new WaterSourceReport(dateTime, reportNum, nameReporter, locationWater, typeWater, conditionWater);

        //store instance into database
        databaseReference.child("reports").child(reportNumStr).setValue(waterReportInstance);

        //update report number
        databaseReference.child("reports").child("reportNum").setValue(reportNumberValue);

        Toast.makeText(SubmitReportActivity.this, "Thank you for submitting a report!", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmitReport) {
            submitWaterReport();
            //finish();
            //startActivity(new Intent(this, HomeActivity.class));
        }
        if (view == buttonCancel) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
