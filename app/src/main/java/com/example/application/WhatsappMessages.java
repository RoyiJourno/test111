package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WhatsappMessages extends AppCompatActivity {

    EditText whatsappString;
    Button btnWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_messages);

        whatsappString = findViewById(R.id.whatsappString);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager packageManager = getPackageManager();
                try{
                    //able to choose who to send the message to.
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    String string = whatsappString.getText().toString();
//
//                    PackageInfo packageInfo = packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_META_DATA);
//
//                    intent.setPackage("com.whatsapp");
//
//                    intent.putExtra(Intent.EXTRA_TEXT,string);
//                    startActivity(Intent.createChooser(intent,"Share with..."));
                    String string = whatsappString.getText().toString();
                    Uri uri = Uri.parse("smsto:"+"972515755077");
                    Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                    intent.setPackage("com.whatsapp");
                    intent.putExtra(Intent.EXTRA_TEXT,string);
                    startActivity(Intent.createChooser(intent,"Share with"));



                }catch (Exception E){
                    Toast.makeText(getApplicationContext(),"Whatsapp not found!!!",Toast.LENGTH_LONG).show();
                }
            }
        });



    }
}
