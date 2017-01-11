package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// http://www.mocky.io/

public interface ApiEndpointInterface {

    @GET("v2/58765627100000e5098b5c58")
    Call<DilemmaApiResponse> getDilemmaList();

//    @GET("services/rest?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1")
//    Call<DilemmaApiResponse> getDilemmaList(@Query("api_key") String apikey,
//                                             @Query("page") int page);

}
