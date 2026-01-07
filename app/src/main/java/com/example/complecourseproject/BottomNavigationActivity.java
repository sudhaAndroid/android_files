package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.complecourseproject.fragments.ParentFragment;

public class BottomNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ParentFragment())
                    .commit();
        }
    }
}