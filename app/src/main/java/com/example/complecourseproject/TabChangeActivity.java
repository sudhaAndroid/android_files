package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.complecourseproject.adapter.ViewPagerAdapter;
import com.example.complecourseproject.fragments.FirstFragment;
import com.example.complecourseproject.fragments.SecondFragment;
import com.example.complecourseproject.fragments.ThirdFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class TabChangeActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_change);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new FirstFragment(), "First");
        adapter.addFragment(new SecondFragment(), "Tab 2");
        adapter.addFragment(new ThirdFragment(), "Tab 3");
        viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(adapter.getPageTitle(position))
        ).attach();
    }
}