package com.bnnvara.kiespijn.Dilemma;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Answer {

    //    @SerializedName("user_fb_id");
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
