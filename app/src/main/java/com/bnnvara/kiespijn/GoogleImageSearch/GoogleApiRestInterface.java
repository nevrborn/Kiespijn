package com.bnnvara.kiespijn.GoogleImageSearch;

import com.bnnvara.kiespijn.GoogleImageSearch.GoogleImageApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface GoogleApiRestInterface {

    @GET("/customsearch/v1")
    Call<GoogleImageApiResponse> customSearch(@Query("key") String key, @Query("cx") String cx, @Query("q") String query);

}

