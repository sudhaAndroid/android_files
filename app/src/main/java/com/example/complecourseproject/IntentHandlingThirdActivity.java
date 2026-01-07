package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class IntentHandlingThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_handling_third);
        TextView txt = findViewById(R.id.txtThird);
        txt.setText(" You opened ThirdActivity using Intent Filter!");
    }
}