package com.example.jayda.team54;

import java.util.ArrayList;

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
 * Created by Josufi on 3/14/17.
 */

public class ViewPurityActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonHome;
    private ListView list;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref;
    private final ArrayList<String> purityArr = new ArrayList<>();

    public void getData() {
        ref = database.getReference();

        // retrieve data from database and add to array for ListView adapter
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                for (DataSnapshot i : data.child("purity").getChildren()) {
                    if (!(i.getKey().equals("reportNum"))) {
                        purityArr.add(i.getKey());
                    }
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(ViewPurityActivity.this,
                        android.R.layout.simple_list_item_1, purityArr);
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
        setContentView(R.layout.activity_view_purity);

        buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(this);

        list = (ListView) findViewById(R.id.list);
        this.getData();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonHome) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
