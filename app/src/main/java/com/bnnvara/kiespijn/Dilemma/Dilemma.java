package com.bnnvara.kiespijn.Dilemma;

import android.util.Log;

import com.bnnvara.kiespijn.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Dilemma implements Serializable {

    @SerializedName("uuid")
    private String mUuid;

    @SerializedName("creator_fb_id")
    private String mCreator_fb_id;

    @SerializedName("creator_fb_name")
    private String mCreator_name;

    @SerializedName("creator_fb_sex")
    private String mCreator_sex;

    @SerializedName("creator_fb_age")
    private String mCreator_age;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("photoA")
    private String mPhotoA;

    @SerializedName("photoB")
    private String mPhotoB;

    @SerializedName("titlePhotoA")
    private String mTitlePhotoA;

    @SerializedName("titlePhotoB")
    private String mTitlePhotoB;

    @SerializedName("createdAt")
    private long mCreatedAt;

    @SerializedName("deadline")
    private long mDeadline;

    @SerializedName("anonymous")
    private boolean mIsAnonymous;

    @SerializedName("isToAll")
    private boolean mIsToAll;

    @SerializedName("replies")
    private Replies mReplies;

    private Boolean isFirstTimeToTargetGroup = true;
    private Boolean isFirstTimeToFromWho = true;

    private String mCreator_picture_url;

    private int mTimeLeft;


    public Dilemma() {

    }


    public String getCreator_name() {
        return mCreator_name;
    }

    public void setCreator_name(String creator_name) {
        mCreator_name = creator_name;
    }

    public String getCreator_sex() {
        return mCreator_sex;
    }

    public void setCreator_sex(String creator_sex) {
        mCreator_sex = creator_sex;
    }

    public String getCreator_age() {
        return mCreator_age;
    }

    public void setCreator_age(String creator_age) {
        mCreator_age = creator_age;
    }

    public void setCreatedAt(long createdAt) {
        mCreatedAt = createdAt;
    }

    public void setDeadline(long deadline) {
        mDeadline = deadline;
    }

    public void setDeadline(int deadline) {
        long timeToAdd = deadline * 3600000;
        mDeadline = (System.currentTimeMillis() + timeToAdd) / 1000L;
    }

    public void setDeadline(String deadline) {
        mDeadline = Long.getLong(deadline);
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid() {
        mUuid = UUID.randomUUID().toString();
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

    public long getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt() {
        mCreatedAt = System.currentTimeMillis() / 1000L;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = Long.getLong(createdAt);
    }

    public long getDeadline() {
        return mDeadline;
    }

    public String getDateAndTime(long date) {
        long tempDate = date * 1000L;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return dateFormat.format(tempDate);
    }

    public boolean getIsAnonymous() {
        return mIsAnonymous;
    }

    public void setIsAnonymous(String anonymous) {
        mIsAnonymous = anonymous.equals("true");
    }

    public boolean getIsToAll() {
        return mIsToAll;
    }

    public void setIsToAll(String isToAll) {
        mIsToAll = isToAll.equals("true");
    }

    private Replies getReplies() {
        return mReplies;
    }

    public void setReplies(Replies replies) {
        mReplies = replies;
    }

    public Boolean getFirstTimeToTargetGroup() {
        return isFirstTimeToTargetGroup;
    }

    public void setFirstTimeToTargetGroup(Boolean firstTimeToTargetGroup) {
        isFirstTimeToTargetGroup = firstTimeToTargetGroup;
    }

    public Boolean getFirstTimeToFromWho() {
        return isFirstTimeToFromWho;
    }

    public void setFirstTimeToFromWho(Boolean firstTimeToFromWho) {
        isFirstTimeToFromWho = firstTimeToFromWho;
    }

    public String getCreator_picture_url() {
        return mCreator_picture_url;
    }

    public void setCreator_picture_url(String creator_picture_url) {
        mCreator_picture_url = creator_picture_url;
    }

    public void setTimeLeft(int timeLeft) {
        mTimeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return mTimeLeft;
    }

    public boolean isAnsweredByCurrentUser() {
        String userFbId = User.getInstance().getUserKey();
        List<Answer> answerList = this.getReplies().getOption1AnswerList();
        answerList.addAll(this.getReplies().getOption2AnswerList());

        for (Answer answer : answerList) {
            Log.i("Dilemma", answer.getUserFbId());
            if (answer.getUserFbId().equals(userFbId)) {
                return true;
            }
        }

        return false;
    }


    public String getCreator_ageRange() {
        String ageToShow;
        int age = Integer.parseInt(mCreator_age);
        if (age <= 16) {
            ageToShow = "0 - 16 jaar";
        } else if (age > 16 && age <= 20) {
            ageToShow = "17 - 20 jaar";
        } else if (age > 20 && age <= 25) {
            ageToShow = "21 - 25 jaar";
        } else if (age > 25 && age <= 30) {
            ageToShow = "26 - 30 jaar";
        } else if (age > 30 && age <= 35) {
            ageToShow = "31 - 35 jaar";
        } else if (age > 35 && age <= 40) {
            ageToShow = "36 - 40 jaar";
        } else {
            ageToShow = "Leeftijd onbekend";
        }
        return ageToShow;
    }
}
