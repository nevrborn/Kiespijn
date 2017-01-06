package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Replies implements Serializable{

    @SerializedName("optionA")
    private List<Answer> mOption1AnswerList;


    @SerializedName("optionB")
    private List<Answer> mOption2AnswerList;



    public List<Answer> getOption1AnswerList() {
        return mOption1AnswerList;
    }

    public void setOption1AnswerList(List<Answer> option1AnswerList) {
        mOption1AnswerList = option1AnswerList;
    }

    public List<Answer> getOption2AnswerList() {
        return mOption2AnswerList;
    }

    public void setOption2AnswerList(List<Answer> option2AnswerList) {
        mOption2AnswerList = option2AnswerList;
    }
}
