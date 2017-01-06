package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Dilemmas {

    @SerializedName("dilemma")
    private List<Dilemma> mDilemmaList;


    List<Dilemma> getDilemmaList() {
        return mDilemmaList;
    }

    public void setDilemmaList(List<Dilemma> dilemmaList) {
        mDilemmaList = dilemmaList;
    }
}
