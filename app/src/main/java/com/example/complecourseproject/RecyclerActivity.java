package com.example.complecourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.example.complecourseproject.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView = findViewById(R.id.recycler);
        itemList = new ArrayList<>();
        addArrayList();
        //setting values to adapter
        adapter = new RecyclerAdapter(getApplicationContext(),itemList);
        //set the layout AS VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set the layout as horizontal
        //recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

        //using recyclerView as gridview
      //  recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1002);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void addArrayList() {
        Item item = new Item();

        item.setId(1);
        item.setTitle("Item1");
        item.setDescription("Description for item 1");

        Item item1 = new Item();
        item1.setId(2);
        item1.setTitle("Item2");
        item1.setDescription ("Description for item 2");

        Item item2 = new Item();
        item2.setId(3);
        item2.setTitle("Item3");
        item2.setDescription("Description for item 3");

        Item item3 = new Item();
        item3.setId(4);
        item3.setTitle("Item4");
        item3.description = "Description for item 4";

        Item item4 = new Item();
        item4.setId(5);
        item4.setTitle("Item5");
        item4.description = "Description for item 5";

        Item item5 = new Item();
        item5.setId(6);
        item5.setTitle("Item5");
        item5.description = "Description for item 5";

        itemList.add(item);
        itemList.add(item4);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item1);
        itemList.add(item5);

        Log.v("checkArrayList",""+itemList.size());
    }
}