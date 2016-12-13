package com.bnnvara.kiespijn.Dilemma;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Answer {

    private String mUserKey;
    private int mChosenAnswer;
    private long mDateAnswered;

    public Answer(String userKey, int chosenAnswer) {
        mUserKey = userKey;
        mChosenAnswer = chosenAnswer;
        mDateAnswered = System.currentTimeMillis() / 1000L;

        // Set user if logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mUserKey = user.getUid();
        }
    }

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String userKey) {
        mUserKey = userKey;
    }

    public int getChosenAnswer() {
        return mChosenAnswer;
    }

    public void setChosenAnswer(int chosenAnswer) {
        mChosenAnswer = chosenAnswer;
    }

    public long getDateAnswered() {
        return mDateAnswered;
    }

    public void setDateAnswered() {
        mDateAnswered = System.currentTimeMillis() / 1000L;
    }
}
