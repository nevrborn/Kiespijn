package com.bnnvara.kiespijn.Dilemma;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Dilemma {

    //    @SerializedName("uuid");
    private String mUuid;

    //    @SerializedName("creator_fb_id");
    private String mCreator_fb_id;

    //    @SerializedName("title");
    private String mTitle;

    //    @SerializedName("photoA");
    private String mPhotoA;

    //    @SerializedName("photoB");
    private String mPhotoB;

    //    @SerializedName("titlePhotoA");
    private String mTitlePhotoA;

    //    @SerializedName("uuid");
    private String mTitlePhotoB;

    //    @SerializedName("createdAt");
    private String mCreatedAt;

    //    @SerializedName("deadline");
    private String mDeadline;

    //    @SerializedName("anonymous");
    private String mAnonymous;

    //    @SerializedName("replies");
    private Replies mReplies;



    public Dilemma() {

    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public String getCreator_fb_id() {
        return mCreator_fb_id;
    }

    public void setCreator_fb_id(String creator_fb_id) {
        mCreator_fb_id = creator_fb_id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPhotoA() {
        return mPhotoA;
    }

    public void setPhotoA(String photoA) {
        mPhotoA = photoA;
    }

    public String getPhotoB() {
        return mPhotoB;
    }

    public void setPhotoB(String photoB) {
        mPhotoB = photoB;
    }

    public String getTitlePhotoA() {
        return mTitlePhotoA;
    }

    public void setTitlePhotoA(String titlePhotoA) {
        mTitlePhotoA = titlePhotoA;
    }

    public String getTitlePhotoB() {
        return mTitlePhotoB;
    }

    public void setTitlePhotoB(String titlePhotoB) {
        mTitlePhotoB = titlePhotoB;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDeadline() {
        return mDeadline;
    }

    public void setDeadline(String deadline) {
        mDeadline = deadline;
    }

    public String getAnonymous() {
        return mAnonymous;
    }

    public void setAnonymous(String anonymous) {
        mAnonymous = anonymous;
    }

    public Replies getReplies() {
        return mReplies;
    }

    public void setReplies(Replies replies) {
        mReplies = replies;
    }
}

//    public Dilemma(String title, String text, String userKey, Option option1, Option option2) {
//        mTitle = title;
//        mText = text;
//        mDateCreated = System.currentTimeMillis() / 1000L;
//        mUserKey = userKey;
//        mOption1 = option1;
//        mOption2 = option2;
//
//        // Set user if logged in
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            mUserKey = user.getUid();
//        }
//    }


//
//    public String getDateCreated() {
//        long tempDate = mDateCreated * 1000L;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
//        return dateFormat.format(tempDate);
//    }
//
//    public void setDateCreated() {
//        mDateCreated = System.currentTimeMillis() / 1000L;
//    }
//
//    public String getDateExpiration() {
//        long tempDate = mDateExpiration * 1000L;
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
//        return dateFormat.format(tempDate);
//    }
//
//    public void setDateExpiration(long dateExpiration) {
//        mDateExpiration = dateExpiration / 1000L;
//    }
