package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class WelcomePage extends AppCompatActivity {

    TextView userName;
    TextView counter;
    EditText guessingNumber;
    Button btnGuess;
    SharedPreferences sharedPref;
    int randNumber;
    Random rand = new Random();
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        userName = findViewById(R.id.userNameWelcomePage);
        counter = findViewById(R.id.counter);
        guessingNumber = findViewById(R.id.guessingNumber);
        btnGuess = findViewById(R.id.btnGuess);

        randNumber = (rand.nextInt(10)) + 1;



        sharedPref = getSharedPreferences("RoyiApp", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String name = sharedPref.getString("userName","Not Found!!!");

        int level = sharedPref.getInt("Level",1);
        if(level == 2){
            startActivity(new Intent(WelcomePage.this,SecondLevel.class));
        }

        userName.setText("Hello, " + name + "\n Welcome to Guessing game. \n first stage you have to guess number " +
                "between 1-10");

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),randNumber + "",Toast.LENGTH_LONG).show();
                int guessNumber = Integer.parseInt(guessingNumber.getText().toString());
                if(guessNumber == randNumber){
                    editor.putInt("Level",2);
                    editor.apply();
                    startActivity(new Intent(WelcomePage.this,SecondLevel.class));
                }
                int counterLeft = Integer.parseInt(counter.getText().toString());
                counterLeft--;
                counter.setText(counterLeft + "");
                if(counterLeft == 0){
                    randNumber = (rand.nextInt(10)) + 1;
                    counter.setText("5");
                }
            }
        });

        (findViewById(R.id.btnLogout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.remove("isLogin");
                editor.apply();
                startActivity(new Intent(WelcomePage.this,MainActivity.class));

            }
        });
    }
}
//hihi//