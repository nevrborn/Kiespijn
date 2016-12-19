package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DilemmaApiResponse {

    @SerializedName("dilemmas")
    private Dilemmas mDillemmas;

    public DilemmaApiResponse() {

    }

    public List<Dilemma> getDilemmaList() {
        return mDillemmas.getDilemmaList();
    }
}
