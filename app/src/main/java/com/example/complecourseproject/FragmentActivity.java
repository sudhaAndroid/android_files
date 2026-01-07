package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        btn1= findViewById(R.id.btn1);
        btn2= findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btn1){
            startIntentActivity("bottom");
        }else if(v.getId()==R.id.btn2){
            startIntentActivity("viewpager");
        }
    }

    private void startIntentActivity(String str) {
        if(str.matches("bottom")){
            Intent intent = new Intent(FragmentActivity.this,BottomNavigationActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(FragmentActivity.this,TabChangeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}