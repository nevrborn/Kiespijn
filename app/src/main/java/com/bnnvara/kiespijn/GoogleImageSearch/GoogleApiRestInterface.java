package com.bnnvara.kiespijn.GoogleImageSearch;

import com.bnnvara.kiespijn.GoogleImageSearch.GoogleImageApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface GoogleApiRestInterface {

    @GET("/customsearch/v1?fields=items(link%2Ctitle)%2CsearchInformation%2FtotalResults&searchType=image")
    Call<GoogleImageApiResponse> customSearch(@Query("key") String key, @Query("cx") String cx, @Query("q") String query, @Query("start") String index);

}

