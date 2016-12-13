package com.bnnvara.kiespijn.Dilemma;

import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Dilemma {

    private String mDilemmaKey;
    private String mTitle;
    private String mText;
    private long mDateCreated;
    private long mDateExpiration;
    private String mUserKey;
    private Option mOption1;
    private Option mOption2;
    private Boolean isAnonymous;

    private ArrayList<Answer> mOption1AnswerList;
    private ArrayList<Answer> mOption2AnswerList;

    public Dilemma(String title, String text, String userKey, Option option1, Option option2) {
        mTitle = title;
        mText = text;
        mDateCreated = System.currentTimeMillis() / 1000L;
        mUserKey = userKey;
        mOption1 = option1;
        mOption2 = option2;

        // Set user if logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mUserKey = user.getUid();
        }
    }

    public String getDilemmaKey() {
        return mDilemmaKey;
    }

    public void setDilemmaKey(String dilemmaKey) {
        mDilemmaKey = dilemmaKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getDateCreated() {
        long tempDate = mDateCreated * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(tempDate);
    }

    public void setDateCreated() {
        mDateCreated = System.currentTimeMillis() / 1000L;
    }

    public String getDateExpiration() {
        long tempDate = mDateExpiration * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(tempDate);
    }

    public void setDateExpiration(long dateExpiration) {
        mDateExpiration = dateExpiration / 1000L;
    }

    public String getUserKey() {
        return mUserKey;
    }

    public void setUserKey(String userKey) {
        mUserKey = userKey;
    }

    public Option getOption1() {
        return mOption1;
    }

    public void setOption1(Option option1) {
        mOption1 = option1;
    }

    public Option getOption2() {
        return mOption2;
    }

    public void setOption2(Option option2) {
        mOption2 = option2;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

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

    public void addAnswerToOption1AnswerList(Answer answer) {
        mOption1AnswerList.add(answer);
    }

    public void addAnswerToOption2AnswerList(Answer answer) {
        mOption2AnswerList.add(answer);
    }
}
