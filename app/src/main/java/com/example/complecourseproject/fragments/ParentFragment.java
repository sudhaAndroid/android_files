package com.example.complecourseproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.complecourseproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private static final String PREFS_NAME = "bottom_nav_prefs";
    private static final String KEY_SELECTED_ITEM = "selected_item_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        bottomNavigationView = view.findViewById(R.id.bottom_nav);

        // Load saved item from SharedPreferences


        //without sharredpreference ctrl+shift+/-> for commanding multiple lines
     /*   bottomNavigationView.setOnItemSelectedListener(item -> {
                    loadFragmentById(item.getItemId());
                    return true;
                });*/


       // if you want sharredPrefernce , go with this lines
        //In Activity context called as getApplicationContext()
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int selectedItemId = prefs.getInt(KEY_SELECTED_ITEM, R.id.nav_home);
        bottomNavigationView.setSelectedItemId(selectedItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Log.d("checkItemId",""+item.getItemId());
            loadFragmentById(item.getItemId());
            prefs.edit().putInt(KEY_SELECTED_ITEM, item.getItemId()).apply();
            return true;
        });
        loadFragmentById(selectedItemId);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadFragmentById(int itemId) {
        Fragment selectedFragment;

        if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        } else if (itemId == R.id.nav_settings) {
            selectedFragment = new SettingsFragment();
        } else {
            selectedFragment = new HomeFragment(); // default
        }


        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
    }
}
