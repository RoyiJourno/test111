package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userName,userPass,userFullName,userPhone,userAddress;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.userPass);
        userAddress = findViewById(R.id.userAddress);
        userFullName = findViewById(R.id.userFullname);
        userPhone = findViewById(R.id.userPhone);

        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("message");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        (findViewById(R.id.btnSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(userName.getText().toString(),
                        userPass.getText().toString())
                        .addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // Write a message to the database
                                    User newUser = new User(userFullName.getText().toString(),
                                            userPhone.getText().toString(),userAddress.getText().toString());
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    myRef = database.getReference("Users").child(user.getUid());
                                    myRef.setValue(newUser);
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.

                                    updateUI(null);
                                }
                                // ...
                            }
                        });
            }
        });


    }

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            startActivity(new Intent(SignupPage.this,MainActivity.class));
        }
        else{
            Toast.makeText(SignupPage.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
