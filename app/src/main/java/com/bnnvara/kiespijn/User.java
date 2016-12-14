package com.bnnvara.kiespijn;

import com.bnnvara.kiespijn.Dilemma.DilemmaProvider;

public class User {

    private String mUserKey;
    private String mName;
    private String mEmail;
    private String mGender;
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

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
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
