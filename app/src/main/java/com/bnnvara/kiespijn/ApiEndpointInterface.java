package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.CapiModel.CapiApiResponse;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// http://www.mocky.io/v2/5889fe2a250000fd19adcf8c

public interface ApiEndpointInterface {

    @GET("v2/5889fe2a250000fd19adcf8c")
    Call<DilemmaApiResponse> getDilemmaList();

    @GET("v2/58779aa40f00009b070d489f")
    Call<CapiApiResponse> getCapiResponse();

}
