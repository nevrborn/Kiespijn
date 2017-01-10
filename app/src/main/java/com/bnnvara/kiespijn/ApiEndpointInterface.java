package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// http://www.mocky.io/

public interface ApiEndpointInterface {

    @GET("v2/5874bbf50f0000f503ec8bad")
    Call<DilemmaApiResponse> getDilemmaList();

//    @GET("services/rest?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1")
//    Call<DilemmaApiResponse> getDilemmaList(@Query("api_key") String apikey,
//                                             @Query("page") int page);

}
