package com.bnnvara.kiespijn;

import android.media.Image;

import com.bnnvara.kiespijn.Dilemma.DilemmaProvider;

public class User {

    private String mUserKey;
    private String mName;
    private String mEmail;
    private Image mProfilePicture;
    private DilemmaProvider mUserCreatedDilemmas;
    private DilemmaProvider mUserAnsweredDilemmas;

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String userKey) {
        mUserKey = userKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public DilemmaProvider getUserCreatedDilemmas() {
        return mUserCreatedDilemmas;
    }

    public void setUserCreatedDilemmas(DilemmaProvider userCreatedDilemmas) {
        mUserCreatedDilemmas = userCreatedDilemmas;
    }

    public DilemmaProvider getUserAnsweredDilemmas() {
        return mUserAnsweredDilemmas;
    }

    public void setUserAnsweredDilemmas(DilemmaProvider userAnsweredDilemmas) {
        mUserAnsweredDilemmas = userAnsweredDilemmas;
    }
}
