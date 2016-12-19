package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulvancappelle on 19-12-16.
 */
public class Dilemmas {

    @SerializedName("dilemma")
    private List<Dilemma> mDilemmaList;


    public List<Dilemma> getDilemmaList() {
        return mDilemmaList;
    }

    public void setDilemmaList(List<Dilemma> dilemmaList) {
        mDilemmaList = dilemmaList;
    }
}
