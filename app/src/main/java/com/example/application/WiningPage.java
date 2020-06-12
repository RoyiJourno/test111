package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WiningPage extends AppCompatActivity {

    private final int DELAY_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wining_page);

        // New Handler to start the thread and give him the time we want him to run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WiningPage.this,WelcomePage.class));
                WiningPage.this.finish();
            }
        },DELAY_TIME);
    }
}
