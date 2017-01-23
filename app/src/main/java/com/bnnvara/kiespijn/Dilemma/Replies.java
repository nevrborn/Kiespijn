package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Replies implements Serializable{

    @SerializedName("optionA")
    private Answer mAnswerA;

    @SerializedName("optionB")
    private Answer mAnswerB;


    public Answer getAnswerA() {
        return mAnswerA;
    }

    public void setAnswerA(Answer answerA) {
        mAnswerA = answerA;
    }

    public Answer getAnswerB() {
        return mAnswerB;
    }

    public void setAnswerB(Answer answerB) {
        mAnswerB = answerB;
    }
}
