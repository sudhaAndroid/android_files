package com.example.complecourseproject.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("posts")
    Call<List<User>> getPostDetails();

    @GET("posts/{id}")
    Call <List<User>> getPostDetails (@Query("id") int id);



}
