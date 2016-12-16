package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by paulvancappelle on 15-12-16.
 */
public class Replies {

    @SerializedName("optionA")
    private ArrayList<Answer> mOption1AnswerList;

    @SerializedName("optionB")
    private ArrayList<Answer> mOption2AnswerList;



    public ArrayList<Answer> getOption1AnswerList() {
        return mOption1AnswerList;
    }

    public void setOption1AnswerList(ArrayList<Answer> option1AnswerList) {
        mOption1AnswerList = option1AnswerList;
    }

    public ArrayList<Answer> getOption2AnswerList() {
        return mOption2AnswerList;
    }

    public void setOption2AnswerList(ArrayList<Answer> option2AnswerList) {
        mOption2AnswerList = option2AnswerList;
    }
}
