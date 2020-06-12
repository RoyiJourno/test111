package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class insertShifts extends AppCompatActivity {

    EditText startTime,endTime;
    Button btnAddShift;
    FirebaseAuth mAuth;
    DatabaseReference myRef;
    String shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_shifts);

        AllAngleExpandableButton button = (AllAngleExpandableButton)findViewById(R.id.button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.home, R.drawable.setting, R.drawable.clock};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(this, drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);

        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {
                //do whatever you want,the param index is counted from startAngle to endAngle,
                //the value is from 1 to buttonCount - 1(buttonCount if aebIsSelectionMode=true)
                Toast.makeText(getApplicationContext(),index + "",Toast.LENGTH_LONG).show();
                switch (index){
                    case 1:mAuth.signOut();startActivity(new Intent(insertShifts.this,MainActivity.class));break;
                }
            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(insertShifts.this,MainActivity.class));
            }
        });

        findViewById(R.id.btnViewShift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(insertShifts.this,ListViewPage.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid()).child("Shifts");

        startTime = findViewById(R.id.startTime);
        endTime = findViewById(R.id.endTime);
        btnAddShift = findViewById(R.id.addShift);


        btnAddShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shift = "start: " + startTime.getText().toString() + " end: " + endTime.getText().toString();
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> list = new ArrayList<>();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            String eachShift = ds.getValue(String.class);
                            list.add(eachShift);
                        }
                        list.add(shift);
                        myRef.setValue(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                myRef.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }
}
