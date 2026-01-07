package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntentHandlingActivity extends AppCompatActivity {
    Button btnExplicit, btnImplicit, btnNative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_handling);
        btnExplicit = findViewById(R.id.btnExplicit);
        btnImplicit = findViewById(R.id.btnImplicit);
        btnNative = findViewById(R.id.btnNative);


        // 1️⃣ Explicit Intent (switch to SecondActivity with data with key value pair)
        btnExplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent explicit = new Intent(IntentHandlingActivity.this, IntentHandlingSecondActivity.class);
                explicit.putExtra("username", "Sudha Pandian");
                explicit.putExtra("age", 28);
                startActivity(explicit);
                finish();
            }
        });

        // 2️⃣ Implicit Intent (open browser)
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent implicit = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com"));
                startActivity(implicit);
            }
        });

        // 3️⃣ Launch Native App (dialer)
        btnNative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dial = new Intent(Intent.ACTION_DIAL);
                dial.setData(Uri.parse("tel:1234567890"));
                startActivity(dial);
            }
        });


    }
}