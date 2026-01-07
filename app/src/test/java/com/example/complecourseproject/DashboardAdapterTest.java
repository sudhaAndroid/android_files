package com.example.complecourseproject;

import static junit.framework.TestCase.assertEquals;

import com.example.complecourseproject.unitTest.DashboardAdapter;
import com.example.complecourseproject.unitTest.UnitTestUser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapterTest {
    @Test
    public void itemCount_isCorrect(){
        List<UnitTestUser> userList = new ArrayList<>();
        UnitTestUser unitTestUser = new UnitTestUser();
        unitTestUser.title = "added data";
        userList.add(unitTestUser);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(userList);
        assertEquals(1,dashboardAdapter.getItemCount());
    }
}
