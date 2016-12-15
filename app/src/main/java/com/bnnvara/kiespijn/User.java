package com.bnnvara.kiespijn;

import android.media.Image;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;

public class User {

    // Singleton
    private static User sCurrentUser;

    private String mUserKey;
    private String mName;
    private String mEmail;
    private String mSex;
    private String mAge;
    private Image mProfilePicture;
    private DilemmaApiResponse mUserCreatedDilemmas;
    private DilemmaApiResponse mUserAnsweredDilemmas;


    private User() {

    }

    public static User getInstance() {
        if (sCurrentUser == null) {
            User user = new User();
            User.setInstance(user);
        }
        return sCurrentUser;
    }

    private static void setInstance(User user) {
        sCurrentUser = user;
    }

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

    public DilemmaApiResponse getUserCreatedDilemmas() {
        return mUserCreatedDilemmas;
    }

    public void setUserCreatedDilemmas(DilemmaApiResponse userCreatedDilemmas) {
        mUserCreatedDilemmas = userCreatedDilemmas;
    }

    public DilemmaApiResponse getUserAnsweredDilemmas() {
        return mUserAnsweredDilemmas;
    }

    public void setUserAnsweredDilemmas(DilemmaApiResponse userAnsweredDilemmas) {
        mUserAnsweredDilemmas = userAnsweredDilemmas;
    }
}
