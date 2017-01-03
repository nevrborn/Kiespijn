package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Answer implements Serializable{

    @SerializedName("user_fb_id")
    private String mUserFbId;


    public Answer(){
    }

    public Answer(String userFbId) {
        mUserFbId = userFbId;
    }

    public String getUserFbId() {
        return mUserFbId;
    }

    public void setUserFbId(String userFbId) {
        mUserFbId = userFbId;
    }
}
