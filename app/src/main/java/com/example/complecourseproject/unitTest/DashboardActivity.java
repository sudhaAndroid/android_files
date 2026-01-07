package com.example.complecourseproject.unitTest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complecourseproject.R;
import com.example.complecourseproject.retrofit.ApiService;
import com.example.complecourseproject.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DashboardAdapter adapter;
    List<UnitTestUser> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiUnitService apiService = RetrofitUnitClient.getApiService();
        apiService.getPostDetails().enqueue(new Callback<List<UnitTestUser>>() {
            @Override
            public void onResponse(Call<List<UnitTestUser>> call, Response<List<UnitTestUser>> response) {
                users.addAll(response.body());
                adapter = new DashboardAdapter(users);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UnitTestUser>> call, Throwable t) {}
        });

    }
}