package com.example.jayda.team54;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private GraphView graph;
    private EditText editTextLocation;
    private EditText editTextYear;
    private Button buttonCreateGraph;
    private Button buttonDone;
    private Spinner spinnerVirusCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        graph = (GraphView) findViewById(R.id.graph);
        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        buttonCreateGraph = (Button) findViewById(R.id.buttonCreateGraph);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        spinnerVirusCon = (Spinner) findViewById(R.id.spinnerVirusContaminant);

        String[] virusConArr = {"Virus", "Contaminant"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, virusConArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVirusCon.setAdapter(adapter);

        buttonCreateGraph.setOnClickListener(this);
        buttonDone.setOnClickListener(this);
    }

    private void createGraph(){
        //pull info from ui elements
        String location = editTextLocation.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();

        //input validation for location
        String[] locationSplit = location.split(",");
        try {
            int lat = Integer.parseInt(locationSplit[0].trim());
            int lng = Integer.parseInt(locationSplit[1].trim());
            if (lat < -90 || lat > 90){
                Toast.makeText(HistoryActivity.this, "Latitude is from -90 to 90", Toast.LENGTH_SHORT).show();
                return;
            }
            if (lng < -180 || lng > 180) {
                Toast.makeText(HistoryActivity.this, "Longitude is from -180 to 180", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e){
            Toast.makeText(HistoryActivity.this, "Please enter location format: LAT,LONG", Toast.LENGTH_SHORT).show();
            return;
        }

        //input validation for year
        try {
            int yr = Integer.parseInt(year);
        } catch (Exception e){
            Toast.makeText(HistoryActivity.this, "Please enter a valid year", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: Read from database here
        //Search through purity reports using getChildren and a for loop
        //Only use report if it matches the year input by manager
        //If there are multiple reports with the same month, average them

        //create actual graph
        DataPoint[] dataPoints = new DataPoint[12];
        for (int i = 0; i < 12; i++){
            dataPoints[i] = new DataPoint(i, 1);    //instead of 1, it should be the virus/contaminant ppm average for that month
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.addSeries(series);
        Toast.makeText(HistoryActivity.this, "Graph created", Toast.LENGTH_SHORT).show();
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
}
