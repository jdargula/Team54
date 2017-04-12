package com.example.jayda.team54;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private Spinner accountSpinner;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //if user is already logged in
        if (firebaseAuth.getCurrentUser() != null) {
            //start activity
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        accountSpinner = (Spinner) findViewById(R.id.spinner2);

        String[] accountArr = {"User", "Worker", "Manager", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }

    /**
     * Checks for valid email and password input, and creates a user within Firebase if the input
     * is valid, and cancels it otherwise.
     */
    public void registerUser()  {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        EmailValidator emailValidator = new EmailValidator();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
            //stopping function execution
            return;
        } else if (!emailValidator.validate(email)) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            //stopping function execution
            return;
        }
        //if validated, register user

        progressDialog.setMessage("One moment please...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //user successfully registered and logged in
                            // start home activity
                            finish();

                            String accountSelection = accountSpinner.getSelectedItem().toString();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            databaseReference.child(user.getUid()).child("account").setValue(accountSelection);
                            databaseReference.child(user.getUid()).child("name").setValue("Name");
                            databaseReference.child(user.getUid()).child("address").setValue("");

                            progressDialog.dismiss();

                            /*if (accountSelection.equals("User")) {
                                startActivity(new Intent(getApplicationContext(),
                                        UserHomeActivity.class));
                            } else {
                                startActivity(new Intent(getApplicationContext(),
                                        HomeActivity.class));
                            }*/
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration unsuccessful, "
                                    + "please try again", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    /**
     *  Method to validate the email entered by the user
     *  @param email email as a string to check for validity
     *  @return valid - true or false based on validity
     */
    public static boolean checkEmail(String email) {
        boolean valid = true;
        if (email == null) {
            valid = false;
            return valid;
        } else {
            if (email.contains("@")) {
                String[] splitEmail = email.split("@");
                if (splitEmail[0].length() < 1) {
                    valid = false;
                }
                if (splitEmail[1].length() < 4) {
                    valid = false;
                }
            } else {
                valid = false;
            }
        }
        return valid;
    }

    /**
     *  Method to validate the password entered by the user
     *  @param pass password as a string to check for valid length
     *  @return valid - true or false based on validity
     */
    public static boolean checkPassword(String pass) {
        boolean valid = true;
        if (pass == null || pass.length() < 2 || pass.contains(" ")) {
            valid = false;
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }

        if (view == textViewSignIn) {
            //will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
