package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.CapiModel.CapiApiResponse;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// http://www.mocky.io/v2/5888936e260000281d96642f

public interface ApiEndpointInterface {

    @GET("v2/5888936e260000281d96642f")
    Call<DilemmaApiResponse> getDilemmaList();

    @GET("v2/58779aa40f00009b070d489f")
    Call<CapiApiResponse> getCapiResponse();

//    @GET("services/rest?method=flickr.photos.getRecent&extras=url_s&format=json&nojsoncallback=1")
//    Call<DilemmaApiResponse> getDilemmaList(@Query("api_key") String apikey,
//                                             @Query("page") int page);

}
