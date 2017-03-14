package com.example.jayda.team54;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class SubmitPurityActivity extends AppCompatActivity implements View.OnClickListener {

    //UI elements
    private EditText editTextDateTime;
    private EditText editTextReportNum;
    private EditText editTextWorkerName;
    private EditText editTextWaterLocation;
    private Spinner spinnerWaterCondition;
    private EditText editTextVirus;
    private EditText editTextContaminant;

    private Button buttonSubmitReport;
    private Button buttonCancel;

    //Firebase stuff
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    //Date/Time Autogeneration
    private TimeZone tz;
    private Calendar calendar;
    private Date currentTime;
    private String currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_purity);

        //Firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //get references to UI elements
        editTextDateTime = (EditText) findViewById(R.id.editTextDateTime);
        editTextReportNum = (EditText) findViewById(R.id.editTextReportNumber);
        editTextWorkerName = (EditText) findViewById(R.id.editTextNameWorker);
        editTextWaterLocation = (EditText) findViewById(R.id.editTextLocationWater);
        spinnerWaterCondition = (Spinner) findViewById(R.id.spinnerConditionWater);
        editTextVirus = (EditText) findViewById(R.id.editTextVirus);
        editTextContaminant = (EditText) findViewById(R.id.editTextContaminant);

        buttonSubmitReport = (Button) findViewById(R.id.buttonSubmitReport);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        //Set up Spinners
        String[] conditionArr = {"Safe", "Treatable", "Unsafe"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditionArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaterCondition.setAdapter(adapter);

        //Set up Buttons
        buttonSubmitReport.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);

        //Autogenerate date and time
        tz = TimeZone.getTimeZone("America/New_York");
        calendar = new GregorianCalendar(tz);
        currentTime = new Date();
        calendar.setTime(currentTime);
        currentDateTime = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH)
                + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":"
                + calendar.get(Calendar.MINUTE);
        editTextDateTime.setText(currentDateTime);

        //Autogenerate name of reporter
        DatabaseReference nameReference = databaseReference.child(user.getUid()).child("name");
        ValueEventListener nameListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                editTextWorkerName.setText(name, TextView.BufferType.EDITABLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nameReference.addValueEventListener(nameListener);

        //Autogenerate Report Number
        //TODO
    }

    private void submitPurityReport(){
        //pull info from ui elements
        String dateTime = editTextDateTime.getText().toString().trim();
        String reportNumStr = editTextReportNum.getText().toString().trim();
        int reportNum = Integer.parseInt(reportNumStr);
        String workerName = editTextWorkerName.getText().toString().trim();
        String waterLocation = editTextWaterLocation.getText().toString().trim();
        String waterCondition = spinnerWaterCondition.getSelectedItem().toString();
        String virusStr = editTextVirus.getText().toString().trim();
        int virus = Integer.parseInt(virusStr);
        String contaminantStr = editTextContaminant.getText().toString().trim();
        int contaminant = Integer.parseInt(contaminantStr);

        //input validation

        //Put information into model
        //WaterPurityReport purityReportInstance = new WaterPurityReport(dateTime, reportNum, workerName, waterLocation, waterCondition, virus, contaminant);

        //Store information into database
        //databaseReference.child("purity").child(reportNumStr).setValue(purityReportInstance);

        Toast.makeText(SubmitPurityActivity.this, "Thank you for submitting a report!", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onClick(View view){
        if (view == buttonSubmitReport){
            //submitPurityReport();
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

        if (view == buttonCancel){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
