package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText userName,userPass;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null){
//            startActivity(new Intent(MainActivity.this,insertShifts.class));
//        }
//        else{
//            //do nothing
//        }
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        sharedPref = getSharedPreferences("RoyiApp",Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        boolean isLogin = sharedPref.getBoolean("isLogin",false);
        if(isLogin){
            startActivity(new Intent(MainActivity.this,WelcomePage.class));
        }
        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.userPass);

        (findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(userName.getText().toString(),
                        userPass.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
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

        (findViewById(R.id.btnSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,SignupPage.class);
                startActivity(i);
            }
        });
    }
    private void updateUI(FirebaseUser user) {
        if(user!=null){
            //move to SMS activity
            //startActivity(new Intent(MainActivity.this,SendSMS.class));

            //InsertShift Activity
//            startActivity(new Intent(MainActivity.this, insertShifts.class));

            //Fragments
            //startActivity(new Intent(MainActivity.this,FragmentPage.class));

            //Send Whatsapp messages
            startActivity(new Intent(MainActivity.this,WhatsappMessages.class));

        }
        else{
            Toast.makeText(MainActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
