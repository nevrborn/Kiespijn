package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by paulvancappelle on 08-11-16.
 */

public interface ApiEndpointInterface {

    @GET("v2/5853c7b90f00007f0cc731cd")
    Call<DilemmaApiResponse> getDilemmaList();

//    @GET("services/rest?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1")
//    Call<DilemmaApiResponse> getDilemmaList(@Query("api_key") String apikey,
//                                             @Query("page") int page);

}
