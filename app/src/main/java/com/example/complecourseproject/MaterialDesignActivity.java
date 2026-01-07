package com.example.complecourseproject;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.complecourseproject.adapter.NoteAdapter;
import com.example.complecourseproject.retrofit.Note;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MaterialDesignActivity extends AppCompatActivity {
    TextInputEditText etTitle, etDesc;
    MaterialButton btnAddNote;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    NoteAdapter adapter;
    ArrayList<Note> noteList;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        btnAddNote = findViewById(R.id.btnAddNote);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        topAppBar = findViewById(R.id.topAppBar);


        noteList = new ArrayList<>();
        adapter = new NoteAdapter(noteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btnAddNote.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();

            if (!title.isEmpty() && !desc.isEmpty()) {
                //list comes from 1
                noteList.add(new Note(title, desc));
                //item position comes from 0
                adapter.notifyItemInserted(noteList.size() - 1);
                etTitle.setText("");
                etDesc.setText("");
            } else {
                Snackbar.make(v, "Please enter both fields", Snackbar.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(v ->
                Snackbar.make(v, "Floating Action Clicked", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!noteList.isEmpty()) {
                                    noteList.remove(noteList.size() - 1);
                                    adapter.notifyItemRemoved(noteList.size());
                                  //  For refreshing adapter
                                          //  adapter.notifyDataSetChanged();
                                }
                            }
                        }).show()

        /*        sudha - 0th (recycler) - 1 st data
                preethamn - 1(recy )- 2nd of list*/

        );

        // Navigation Drawer toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, topAppBar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView item clicks
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Snackbar.make(recyclerView, "Home clicked", Snackbar.LENGTH_SHORT).show();
                } else if (id == R.id.nav_settings) {
                    Snackbar.make(recyclerView, "Settings clicked", Snackbar.LENGTH_SHORT).show();
                } else if (id == R.id.nav_about) {
                    Snackbar.make(recyclerView, "About clicked", Snackbar.LENGTH_SHORT).show();
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }



}

