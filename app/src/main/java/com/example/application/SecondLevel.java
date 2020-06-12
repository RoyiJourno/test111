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

public class SecondLevel extends AppCompatActivity {

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
        setContentView(R.layout.activity_second_level);
        userName = findViewById(R.id.userNameWelcomePage);
        counter = findViewById(R.id.counter);
        guessingNumber = findViewById(R.id.guessingNumber);
        btnGuess = findViewById(R.id.btnGuess);

        randNumber = (rand.nextInt(20)) + 1;


        sharedPref = getSharedPreferences("RoyiApp", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String name = sharedPref.getString("userName","Not Found!!!");

        userName.setText("Hello, " + name + "\n Welcome to Guessing game. \n second stage you have to guess number " +
                "between 1-20");

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),randNumber + "",Toast.LENGTH_LONG).show();
                int guessNumber = Integer.parseInt(guessingNumber.getText().toString());
                if(guessNumber == randNumber){
                    editor.putInt("Level",1);
                    editor.apply();
                    startActivity(new Intent(SecondLevel.this,WiningPage.class));
                }
                int counterLeft = Integer.parseInt(counter.getText().toString());
                counterLeft--;
                counter.setText(counterLeft + "");
                if(counterLeft == 0){
                    editor.putInt("Level",1);
                    editor.apply();
                    startActivity(new Intent(SecondLevel.this,WelcomePage.class));
                }
            }
        });
    }
}
