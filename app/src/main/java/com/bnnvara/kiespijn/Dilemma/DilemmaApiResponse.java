package com.bnnvara.kiespijn.Dilemma;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DilemmaApiResponse {

    @SerializedName("dilemmas")
    private  ArrayList<Dilemma> mDilemmaList;



    public ArrayList<Dilemma> getDilemmaList() {
        return mDilemmaList;
    }

    public void setDilemmaList(ArrayList<Dilemma> dilemmaList) {
        mDilemmaList = dilemmaList;
    }
}
