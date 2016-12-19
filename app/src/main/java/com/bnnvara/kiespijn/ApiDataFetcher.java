package com.bnnvara.kiespijn;

import android.util.Log;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

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
    private static final String TAG = "kiespijn.ApiDataFetcher";
    private ArrayList<Dilemma> mDilemmaList;


    public ApiDataFetcher() {
        //this.getData();
    }

    public void getData(){

        // Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiEndpointInterface apiResponse = retrofit.create(ApiEndpointInterface.class);

        apiResponse.getDilemmaList().enqueue(new Callback<DilemmaApiResponse>() {
            @Override
            public void onResponse(Call<DilemmaApiResponse> call, Response<DilemmaApiResponse> response) {
                Log.e(TAG, "Retrofit response");
                setResponse(response);
            }

            @Override
            public void onFailure(Call<DilemmaApiResponse> call, Throwable t) {
                Log.e(TAG, "Retrofit error: " + t.getMessage());
            }
        });

    }

    private void setResponse(Response<DilemmaApiResponse> response) {
        DilemmaApiResponse mDilemmaApiResponse = response.body();
        if (response.body() == null) {
            Log.e(TAG, "Retrofit body null: " + String.valueOf(response.code()));
        }
        mDilemmaList = mDilemmaApiResponse.getDilemmaList();
        //Log.v("mDilemmaList", String.valueOf(response.body().getDilemmaList().size()));
    }

    public ArrayList<Dilemma> getDilemmaList() {
        return mDilemmaList;
    }
}
