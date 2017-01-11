package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Replies implements Serializable{

    @SerializedName("optionA")
    private Answer mOptionAAnswers;


    @SerializedName("optionB")
    private Answer mOptionBAnswers;

    public Answer getOptionAAnswers() {
        return mOptionAAnswers;
    }

    public void setOptionAAnswers(Answer optionAAnswers) {
        mOptionAAnswers = optionAAnswers;
    }

    public Answer getOptionBAnswers() {
        return mOptionBAnswers;
    }

    public void setOptionBAnswers(Answer optionBAnswers) {
        mOptionBAnswers = optionBAnswers;
    }
}
