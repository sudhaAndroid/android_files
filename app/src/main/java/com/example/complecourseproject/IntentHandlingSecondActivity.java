package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class IntentHandlingSecondActivity extends AppCompatActivity {

    TextView txtData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_handling_second);
        txtData = findViewById(R.id.txtData);

        // Receive data
        String username = getIntent().getStringExtra("username");
        int age = getIntent().getIntExtra("age",123);
        //String age_str = String.valueOf(age);
        /*String age = getIntent().getStringExtra("age");
        int values =Integer.parseInt(age);*/

        txtData.setText("Welcome " + username + ", Age: " + age);
    }
}