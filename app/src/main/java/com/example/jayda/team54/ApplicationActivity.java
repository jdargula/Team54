package com.example.jayda.team54;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApplicationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextName, editTextAddress;
    private Button buttonSaveInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            //user is not logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //assign profile view widgets
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        buttonSaveInformation = (Button) findViewById(R.id.buttonSaveInformation);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome, " + user.getEmail());
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        // add listeners to buttons
        buttonLogout.setOnClickListener(this);
        buttonSaveInformation.setOnClickListener(this);
    }

    /**
     * Takes input for user's name and address, and adds the data to the database.
     */
    private void saveUserInformation() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        //UserInformation userInformation = new UserInformation(name, address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        //databaseReference.child(user.getUid()).setValue(userInformation);

        databaseReference.child(user.getUid()).child("name").setValue(name);
        databaseReference.child(user.getUid()).child("address").setValue(address);

        Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == buttonSaveInformation) {
            saveUserInformation();
            if (view == buttonSaveInformation) {
                saveUserInformation();
                finish();
                startActivity(new Intent(this, HomeActivity.class));
            }
        }
    }
}
