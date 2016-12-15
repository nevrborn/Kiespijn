package com.bnnvara.kiespijn.Dilemma;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DilemmaApiResponse {

    //    @SerializedName("dilemmas");
    private  List<Dilemma> mDilemmaList;



    public List<Dilemma> getDilemmaList() {
        return mDilemmaList;
    }

    public void setDilemmaList(List<Dilemma> dilemmaList) {
        mDilemmaList = dilemmaList;
    }
}
