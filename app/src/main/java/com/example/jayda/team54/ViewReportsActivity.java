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
 * Created by Emily on 2/25/2017.
 */

public class ViewReportsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button buttonHome;
    private ListView list;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private final ArrayList<String> reportsArr = new ArrayList<>();

    /**
     * Get data from database and add to array for use in ListView adapter
     */
    public void getData() {
        ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot i : data.child("reports").getChildren()) {
                    if (!(i.getKey().equals("reportNum"))) {
                        reportsArr.add(i.getKey());
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ViewReportsActivity.this,
                        android.R.layout.simple_list_item_1, reportsArr);
                list.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
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
