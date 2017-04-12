package com.example.jayda.team54;
import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Activity to view water reports.
 */

public class ViewReportsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button buttonHome;
    private ListView list;
    private final ArrayList<String> reportsArr = new ArrayList<>();
    private int reportNum;

    /**
     * Get data from database and add to array for use in ListView adapter
     */

    public void getData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot i : data.child("reports").getChildren()) {
                    if (!(i.getKey().equals("reportNum"))) {
                        reportsArr.add(i.getKey());
                    }
                    if (i.getKey().equals("reportNum")) {
                        reportNum = Integer.parseInt(i.getKey());
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ViewReportsActivity.this,
                        android.R.layout.simple_list_item_1, reportsArr);
                list.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    /**
     * Method to get the number of elements in the database.
     * @return reportNum - the number of last report submitted
     */

    public int getReportNum() {
        return reportNum;
    }

    /**
     * Get the reports array.
     * @return reportsArr - an array of water report by number
     */

    public ArrayList<String> getReportsArr() {
        return reportsArr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(this);

        list = (ListView) findViewById(R.id.list);
        this.getData();
        list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonHome) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id){
        finish();
        Intent intent = new Intent();
        String key = reportsArr.get(position);
        intent.setClass(this, ReportDetailActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}
