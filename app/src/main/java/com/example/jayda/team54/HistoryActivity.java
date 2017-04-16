package com.example.jayda.team54;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final ArrayList[] cMonthArr = new ArrayList[12];
    private final ArrayList[] vMonthArr = new ArrayList[12];

    private GraphView graph;
    private EditText editTextLocation;
    private EditText editTextYear;
    private Button buttonCreateGraph;
    private Button buttonDone;
    private Spinner spinnerVirusCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < 12; i++) {
            cMonthArr[i] = new ArrayList<>();
            vMonthArr[i] = new ArrayList<>();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        graph = (GraphView) findViewById(R.id.graph);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        buttonCreateGraph = (Button) findViewById(R.id.buttonCreateGraph);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        spinnerVirusCon = (Spinner) findViewById(R.id.spinnerVirusContaminant);

        String[] virusConArr = {"Virus", "Contaminant"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, virusConArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVirusCon.setAdapter(adapter);

        buttonCreateGraph.setOnClickListener(this);
        buttonDone.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * Pulls input from the ui elements, then creates a graph based on that info, with time on the
     * x-axis and virus/contaminant ppm on the y-axis
     */
    private void createGraph(){
        //pull info from ui elements
        String location = editTextLocation.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();

        if (!validateGraph(location, year)) {
            return;
        }
        final int yearInt = Integer.parseInt(year);

        //reading info from database; get virus/contaminant ppm data for relevant year/location
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                HashMap pReport;
                for (DataSnapshot i : data.child("purity").getChildren()) {
                    if (!(i.getKey().equals("reportNum"))) {
                        pReport = (HashMap) i.getValue();
                        String dateValue = pReport.get("dateTime").toString();
                        Calendar reportDate = new GregorianCalendar();
                        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
                        Long cPPM = (Long) pReport.get("contaminantPPM");
                        Long vPPM = (Long) pReport.get("virusPPM");
                        try {
                            Date date = format.parse(dateValue);
                            reportDate.setTime(date);
                            int reportYear = reportDate.get(Calendar.YEAR);
                            if (reportYear == yearInt) {
                                int reportMonth = reportDate.get(Calendar.MONTH);
                                cMonthArr[reportMonth].add(cPPM);
                                vMonthArr[reportMonth].add(vPPM);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }

        });

        //average the virus/contaminant ppm if they fall on the same month
        int[] contamAvg = new int[12];
        for (int month = 0; month < 12; month ++) {
            int contamTot = 0;
            int contCount = 0;
            if (cMonthArr[month] != null) {
                for (Object i : cMonthArr[month]) {
                    contamTot += (Long) i;
                    contCount++;
                }
            }
            if (contCount != 0) {
                contamAvg[month] = contamTot / contCount;
            } else {
                contamAvg[month] = 0;
            }
        }

        int[] virusAvg = new int[12];

        for (int month = 0; month < 12; month++) {
            int virusTot = 0;
            int virCount = 0;
            if (vMonthArr[month] != null) {
                for (Object i : vMonthArr[month]) {
                    virusTot += (Long) i;
                    virCount++;
                }
            }
            if (virCount != 0) {
                virusAvg[month] = virusTot / virCount;
            } else {
                virusAvg[month] = 0;
            }
        }

        //create actual graph
        DataPoint[] dataPoints = new DataPoint[12];
        String waterCondition = spinnerVirusCon.getSelectedItem().toString();
        if (waterCondition.equals("Virus")) {
            for (int i = 0; i < 12; i++){
                dataPoints[i] = new DataPoint(i, virusAvg[i]);    //instead of 1, it should be the virus/contaminant ppm average for that month
            }
        } else {
            for (int i = 0; i < 12; i++){
                dataPoints[i] = new DataPoint(i, contamAvg[i]);    //instead of 1, it should be the virus/contaminant ppm average for that month
            }
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
        Toast.makeText(HistoryActivity.this, "Graph created", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to validate input from the user to create the graph.
     * @param location A string location to check if valid
     * @param year A string year to check if valid
     * @return A boolean based on whether input strings are valid or not
     */
    public static boolean validateGraph(String location, String year){
        //input validation for location
        String[] locationSplit = location.split(",");
        try {
            int lat = Integer.parseInt(locationSplit[0].trim());
            int lng = Integer.parseInt(locationSplit[1].trim());
            if (lat < -90 || lat > 90){
                //Toast.makeText(this, "Latitude is from -90 to 90", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (lng < -180 || lng > 180) {
                //Toast.makeText(HistoryActivity.this, "Longitude is from -180 to 180", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e){
            //Toast.makeText(HistoryActivity.this, "Please enter location format: LAT,LONG", Toast.LENGTH_SHORT).show();
            return false;
        }

        //input validation for year
        try {
            int yr = Integer.parseInt(year);
        } catch (Exception e){
            //Toast.makeText(HistoryActivity.this, "Please enter a valid year", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view){
        if (view == buttonCreateGraph){
            createGraph();
        }

        if (view == buttonDone){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
