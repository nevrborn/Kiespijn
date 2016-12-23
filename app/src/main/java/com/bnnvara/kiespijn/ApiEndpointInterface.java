package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by paulvancappelle on 08-11-16.
 */

// http://www.mocky.io/

public interface ApiEndpointInterface {

    @GET("v2/585d24ce1000001c0f501e12")
    Call<DilemmaApiResponse> getDilemmaList();

//    @GET("services/rest?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1")
//    Call<DilemmaApiResponse> getDilemmaList(@Query("api_key") String apikey,
//                                             @Query("page") int page);

}
