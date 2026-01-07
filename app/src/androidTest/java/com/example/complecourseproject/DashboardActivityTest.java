package com.example.complecourseproject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.complecourseproject.unitTest.DashboardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DashboardActivityTest
{
    @Rule
    public ActivityScenarioRule<DashboardActivity> rule =
            new ActivityScenarioRule<>(DashboardActivity.class);


    @Test
    public void recyclerView_isVisible() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()));
    }
}
