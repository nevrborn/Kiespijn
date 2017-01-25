package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.CapiModel.CapiApiResponse;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// http://www.mocky.io/v2/5888abce260000ac1e966494

public interface ApiEndpointInterface {

    @GET("v2/588930cd250000dc09adce55")
    Call<DilemmaApiResponse> getDilemmaList();

    @GET("v2/58779aa40f00009b070d489f")
    Call<CapiApiResponse> getCapiResponse();

}
