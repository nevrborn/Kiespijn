package com.bnnvara.kiespijn;

import android.util.Log;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulvancappelle on 16-12-16.
 */

public class ApiDataFetcher {

    private static final String BASE_URL = "http://www.mocky.io/";
    private List<Dilemma> mDilemmaList;


    public void getData(){

        // Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiResponse = retrofit.create(ApiEndpointInterface.class);

        apiResponse.getDilemmaList().enqueue(new Callback<DilemmaApiResponse>() {
            @Override
            public void onResponse(Call<DilemmaApiResponse> call, Response<DilemmaApiResponse> response) {
                setResponse(response);
            }

            @Override
            public void onFailure(Call<DilemmaApiResponse> call, Throwable t) {
                Log.e("Retrofit error", t.getMessage());
            }
        });

    }

    private void setResponse(Response<DilemmaApiResponse> response) {
        DilemmaApiResponse mDilemmaApiResponse = response.body();
        if (response.body() == null) {
            Log.e("Retrofit body null", String.valueOf(response.code()));
        }
        mDilemmaList = mDilemmaApiResponse.getDilemmaList();
        Log.v("mDilemmaList", String.valueOf(response.body().getDilemmaList().size()));
    }

}
