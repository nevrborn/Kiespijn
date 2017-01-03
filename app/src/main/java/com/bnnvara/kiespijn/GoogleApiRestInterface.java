package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.CreateDilemmaPage.GoogleImageApiResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleApiRestInterface {

    @GET("/customsearch/v1")
    Call<GoogleImageApiResponse> customSearch(@Query("key") String key, @Query("cx") String cx, @Query("q") String query);

}

