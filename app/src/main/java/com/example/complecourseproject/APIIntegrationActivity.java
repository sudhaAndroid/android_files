package com.example.complecourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.complecourseproject.retrofit.ApiAdapter;
import com.example.complecourseproject.retrofit.ApiService;
import com.example.complecourseproject.retrofit.RetrofitClient;
import com.example.complecourseproject.retrofit.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIIntegrationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiAdapter adapter;
    List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apiintegration);
        recyclerView = findViewById(R.id.recycler);
        userList = new ArrayList<>();

        //code to integrate api url with java code
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<User>> call = apiService.getPostDetails();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("checkResponse",""+response.body().size());
                userList.addAll(response.body());
                addListToRecycler(userList);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("checkFailureResult",t.getMessage());
            }
        });



    }

     void addListToRecycler(List<User> userList) {
        Log.d("checkUserList",""+userList.size());
        adapter = new ApiAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}