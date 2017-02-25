package com.example.jayda.team54;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Emily on 2/24/2017.
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

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_report);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //find references to ui elements
        buttonSubmitReport = (Button) findViewById(R.id.buttonSubmitReport);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        editTextDateTime = (EditText) findViewById(R.id.editTextDateTime);
        editTextReportNumber = (EditText) findViewById(R.id.editTextReportNumber);
        editTextNameReporter = (EditText) findViewById(R.id.editTextNameReporter);
        editTextLocationWater = (EditText) findViewById(R.id.editTextLocationWater);
        spinnerTypeWater = (Spinner) findViewById(R.id.spinnerTypeWater);
        spinnerConditionWater = (Spinner) findViewById(R.id.spinnerConditionWater);

        String[] typeWaterArr = {"Bottled", "Well", "Stream", "Lake", "Spring", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeWaterArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeWater.setAdapter(adapter);

        String[] conditionWaterArr = {"Waste", "Treatable-Clear", "Treatable-Muddy", "Potable"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionWaterArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConditionWater.setAdapter(adapter1);

        buttonSubmitReport.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

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

        //we should probably do some kind of input validation here

        //enter info into a WaterSourceReport instance
        WaterSourceReport waterReportInstance = new WaterSourceReport(dateTime, reportNum, nameReporter, locationWater, typeWater, conditionWater);

        //store instance into database
        databaseReference.child("reports").child(reportNumStr).setValue(waterReportInstance);

        Toast.makeText(SubmitReportActivity.this, "Thank you for submitting a report!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmitReport) {
            submitWaterReport();
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
        if (view == buttonCancel) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
