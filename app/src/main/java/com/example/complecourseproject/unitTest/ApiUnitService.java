package com.example.complecourseproject.unitTest;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiUnitService {
    @GET("posts")
    Call<List<UnitTestUser>> getPostDetails();

    @GET("posts/{id}")
    Call <List<UnitTestUser>> getPostDetails (@Query("id") int id);
}
