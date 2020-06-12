package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendSMS extends AppCompatActivity {

    EditText text,number;
    final int REQUEST_PREMISSION_CODE = 7771;
    SmsManager smsManager;
    FirebaseAuth mAuth;
    TextView hello;
    DatabaseReference myRefUsers = FirebaseDatabase.getInstance().getReference("Users");
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Read from the database
        myRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.child(user.getUid())
                        .getValue(User.class);
                hello.setText("Hello, " + value.getFullName());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        hello = findViewById(R.id.helloUser);



        number = findViewById(R.id.number);
        text = findViewById(R.id.message1);

        smsManager = SmsManager.getDefault();

        (findViewById(R.id.btnSignout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(SendSMS.this,MainActivity.class));
            }
        });

        (findViewById(R.id.btnSend)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "+972" +
                        number.getText().toString().substring(1), Toast.LENGTH_LONG).show();

                //start the dialog to ask from the user permission.
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity)SendSMS.this,new String[]
                            {Manifest.permission.SEND_SMS},REQUEST_PREMISSION_CODE);
                }
                // if you already had permission,
                //you can send message without asking the user.
                else {
                    smsManager.sendTextMessage("+972" +
                                    number.getText().toString().substring(1), null,
                            text.getText().toString(), null, null);
                }
            }
        });
    }

    //after the user choose option from the dialog,
    //we returning here.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PREMISSION_CODE:
                //if the grant permission length bigger then 1,
                //the user allow you to send SMS
                if(grantResults.length>1) {
                    smsManager.sendTextMessage("+972" +
                                    number.getText().toString().substring(1), null,
                            text.getText().toString(), null, null);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Permission denied cant send the sms."
                    ,Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
