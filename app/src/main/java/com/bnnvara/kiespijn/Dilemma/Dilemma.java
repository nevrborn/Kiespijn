package com.bnnvara.kiespijn.Dilemma;

import com.bnnvara.kiespijn.ContentPage.Contents;
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

    @SerializedName("creator_fb_url")
    private String mCreator_picture_url;

    @SerializedName("creator_hometown")
    private String mCreator_hometown;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("backgroundInfo")
    private String mBackgroundInfo;

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

    @SerializedName("content")
    private Contents mContents;

    private List<String> mTargetIDList;

    private Boolean isFirstTimeToTargetGroup = true;
    private Boolean isFirstTimeToFromWho = true;
    private int mRandomCallerIndex = 0;
    private String mRandomGifURL = "";


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

    public String getBackgroundInfo() {
        return mBackgroundInfo;
    }

    public void setBackgroundInfo(String backgroundInfo) {
        mBackgroundInfo = backgroundInfo;
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

    public Contents getContents() {
        return mContents;
    }

    public void setContents(Contents contents) {
        mContents = contents;
    }

    public boolean isAnsweredByCurrentUser() {
        String userFbId = User.getInstance().getUserKey();
        List<String> answerList = this.getReplies().getAnswerA().getAnswerFacebookIDs();
        answerList.addAll(this.getReplies().getAnswerB().getAnswerFacebookIDs());

        for (String answerID : answerList) {
            if (answerID.contains(userFbId)) {
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

    public Boolean isFromAFriend() {
        User user = User.getInstance();

        int i = 0;

        while (i < user.mFacebookFriendList.size()) {
            if (user.mFacebookFriendList.get(i).getFacebookID().equals(mCreator_fb_id)) {
                return true;
            }

            i += 1;
        }

        return false;
    }

    public String getCreator_hometown() {

        if (mCreator_hometown == null || mCreator_hometown.equals("")) {
            mCreator_hometown = "Onbekend";
        }

        return mCreator_hometown;
    }

    public void setCreator_hometown(String creator_hometown) {
        mCreator_hometown = creator_hometown;
    }

    public List<String> getTargetIDList() {
        return mTargetIDList;
    }

    public void setTargetIDList(List<String> targetIDList) {
        mTargetIDList = targetIDList;
    }

    public int getRandomCallerIndex() {
        return mRandomCallerIndex;
    }

    public void setRandomCallerIndex(int randomCallerIndex) {
        mRandomCallerIndex = randomCallerIndex;
    }

    public String getRandomGifURL() {
        return mRandomGifURL;
    }

    public void setRandomGifURL(String randomGifURL) {
        mRandomGifURL = randomGifURL;
    }



    /*
    * All methods to dertmine the the statistics for the Result Page
    *
    * */
    public int getVotesFromMenA() {
        return this.getReplies().getAnswerA().getAnswerFromMen();
    }

    public int getVotesFromMenB() {
        return this.getReplies().getAnswerB().getAnswerFromMen();
    }

    public int getVotesFromMen() {
        return this.getVotesFromMenA() + this.getVotesFromMenB();
    }

    public int getVotesFromWomenA() {
        return this.getReplies().getAnswerA().getAnswerFromWomen();
    }

    public int getVotesFromWomenB() {
        return this.getReplies().getAnswerB().getAnswerFromWomen();
    }

    public int getVotesFromWomen() {
        return this.getVotesFromWomenA() + this.getVotesFromWomenB();
    }

    public int getVotesFromUnknownSexA() {
        return this.getReplies().getAnswerA().getAnswerFromUnknownSex();
    }

    public int getVotesFromUnknownSexB() {
        return this.getReplies().getAnswerB().getAnswerFromUnknownSex();
    }

    public int getVotesFromUnknownSex() {
        return this.getVotesFromUnknownSexA() + this.getVotesFromUnknownSexB();
    }

    public int getTotalVotesA() {
        return this.getVotesFromMenA()
                + this.getVotesFromWomenA()
                + this.getVotesFromUnknownSexA();
    }

    public int getTotalVotesB() {
        return this.getVotesFromMenB()
                + this.getVotesFromWomenB()
                + this.getVotesFromUnknownSexB();
    }

    public int getTotalVotes() {
        return this.getVotesFromMen()
                + this.getVotesFromWomen()
                + this.getVotesFromUnknownSex();
    }

    public int getVotesFromAgeGroup1A(){
        return this.getReplies().getAnswerA().getAnswerFromAgeGroup1();
    }

    public int getVotesFromAgeGroup1B(){
        return this.getReplies().getAnswerB().getAnswerFromAgeGroup1();
    }

    public int getVotesFromAgeGroup1() {
        return this.getVotesFromAgeGroup1A()
                + this.getVotesFromAgeGroup1B();
    }

    public int getVotesFromAgeGroup2A(){
        return this.getReplies().getAnswerA().getAnswerFromAgeGroup2();
    }

    public int getVotesFromAgeGroup2B(){
        return this.getReplies().getAnswerB().getAnswerFromAgeGroup2();
    }

    public int getVotesFromAgeGroup2() {
        return this.getVotesFromAgeGroup2A()
                + this.getVotesFromAgeGroup2B();
    }

    public int getVotesFromAgeGroup3A(){
        return this.getReplies().getAnswerA().getAnswerFromAgeGroup3();
    }

    public int getVotesFromAgeGroup3B(){
        return this.getReplies().getAnswerB().getAnswerFromAgeGroup3();
    }

    public int getVotesFromAgeGroup3() {
        return this.getVotesFromAgeGroup3A()
                + this.getVotesFromAgeGroup3B();
    }

    public int getVotesFromAgeGroup4A(){
        return this.getReplies().getAnswerA().getAnswerFromAgeGroup4();
    }

    public int getVotesFromAgeGroup4B(){
        return this.getReplies().getAnswerB().getAnswerFromAgeGroup4();
    }

    public int getVotesFromAgeGroup4() {
        return this.getVotesFromAgeGroup4A()
                + this.getVotesFromAgeGroup4B();
    }

    public int getVotesFromAgeGroupUnknownA(){
        return this.getReplies().getAnswerA().getAnswerFromAgeGroupUnknown();
    }

    public int getVotesFromAgeGroupUnknownB(){
        return this.getReplies().getAnswerB().getAnswerFromAgeGroupUnknown();
    }

    public int getVotesFromAgeGroupUnknown() {
        return this.getVotesFromAgeGroupUnknownA()
                + this.getVotesFromAgeGroupUnknownB();
    }



    public int getScoreA() {
        double score = (double) (this.getTotalVotesA() * 100) / this.getTotalVotes();
        return (int) Math.round(score);
    }

    public int getScoreB() {
        double score = (double) (this.getTotalVotesB() * 100) / this.getTotalVotes();
        return (int) Math.round(score);
    }

    public int getScoreMenA() {
        double score = (double) (this.getVotesFromMenA() * 100) / this.getVotesFromMen();
        return (int) Math.round(score);
    }

    public int getScoreMenB() {
        double score = (double) (this.getVotesFromMenB() * 100) / this.getVotesFromMen();
        return (int) Math.round(score);
    }



    public int getScoreAgeGroup1A() {
        double score = (double) (this.getVotesFromAgeGroup1A() * 100) / this.getVotesFromAgeGroup1();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup1B() {
        double score = (double) (this.getVotesFromAgeGroup1B() * 100) / this.getVotesFromAgeGroup1();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup2A() {
        double score = (double) (this.getVotesFromAgeGroup2A() * 100) / this.getVotesFromAgeGroup2();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup2B() {
        double score = (double) (this.getVotesFromAgeGroup2B() * 100) / this.getVotesFromAgeGroup2();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup3A() {
        double score = (double) (this.getVotesFromAgeGroup3A() * 100) / this.getVotesFromAgeGroup3();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup3B() {
        double score = (double) (this.getVotesFromAgeGroup3B() * 100) / this.getVotesFromAgeGroup3();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup4A() {
        double score = (double) (this.getVotesFromAgeGroup4A() * 100) / this.getVotesFromAgeGroup4();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroup4B() {
        double score = (double) (this.getVotesFromAgeGroup4B() * 100) / this.getVotesFromAgeGroup4();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroupUnknownA() {
        double score = (double) (this.getVotesFromAgeGroupUnknownA() * 100) / this.getVotesFromAgeGroupUnknown();
        return (int) Math.round(score);
    }

    public int getScoreAgeGroupUnknownB() {
        double score = (double) (this.getVotesFromAgeGroupUnknownB() * 100) / this.getVotesFromAgeGroupUnknown();
        return (int) Math.round(score);
    }

    public int getContentCountA() {
        return mContents.getOptionAContent().size();
    }

    public int getContentCountB() {
        return mContents.getOptionBContent().size();
    }

}
