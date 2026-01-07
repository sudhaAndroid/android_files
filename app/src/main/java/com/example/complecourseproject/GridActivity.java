package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.complecourseproject.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {
    List<Item> itemList;
    GridAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        GridView gridView = findViewById(R.id.grid_view);
        itemList = new ArrayList<>();
        addArrayList();
        adapter = new GridAdapter(this, itemList);
        gridView.setAdapter(adapter);

        // Set number of columns (optional - can also be set in XML)
        gridView.setNumColumns(3);

        // Set click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) parent.getItemAtPosition(position);
                Toast.makeText(GridActivity.this, "Clicked: " + item.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addArrayList() {
        Item item = new Item();
        item.setId(1);
        item.title= "Apple";
        item.description = "Vitamin A";

        Item item1 = new Item();
        item1.setId(2);
        item1.title= "Orange";
        item1.description = "Vitamin C";

        Item item2 = new Item();
        item2.setId(3);
        item2.title= "Banana";
        item2.description = "Vitamin K";

        Item item3 = new Item();
        item3.setId(4);
        item3.title= "Papaya";
        item3.description = "Vitamin A";

        Item item4 = new Item();
        item4.setId(5);
        item4.title= "Pinapple";
        item4.description = "Vitamin N";

        itemList.add(item);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);

        Log.d("checkArrayList",""+itemList.size());
    }
}