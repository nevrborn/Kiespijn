package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

    @SerializedName("answer_from_men")
    private int mAnswerFromMen;

    @SerializedName("answer_from_women")
    private int mAnswerFromWomen;

    @SerializedName("answer_from_unknow_sex")
    private int mAnswerFromUnknownSex;

    @SerializedName("answer_from_agegroup_1")
    private int mAnswerFromAgeGroup1;

    @SerializedName("answer_from_agegroup_2")
    private int mAnswerFromAgeGroup2;

    @SerializedName("answer_from_agegroup_3")
    private int mAnswerFromAgeGroup3;

    @SerializedName("answer_from_agegroup_4")
    private int mAnswerFromAgeGroup4;

    @SerializedName("answer_from_agegroup_unknown")
    private int mAnswerFromAgeGroupUnknown;

    @SerializedName("answer_from_same_location")
    private int mAnswerFromSameLocation;

    @SerializedName("answer_facebook_id_list")
    private List<String> mAnswerFacebookIDs;



    public Answer() {
    }

    public Answer(int answerFromMen, int answerFromWomen, int answerFromUnknownSex, int answerFromAgeGroup1, int answerFromAgeGroup2, int answerFromAgeGroup3, int answerFromAgeGroup4, int answerFromAgeGroupUnknown, int answerFromSameLocation, List<String> answerFacebookIDs) {
        mAnswerFromMen = answerFromMen;
        mAnswerFromWomen = answerFromWomen;
        mAnswerFromUnknownSex = answerFromUnknownSex;
        mAnswerFromAgeGroup1 = answerFromAgeGroup1;
        mAnswerFromAgeGroup2 = answerFromAgeGroup2;
        mAnswerFromAgeGroup3 = answerFromAgeGroup3;
        mAnswerFromAgeGroup4 = answerFromAgeGroup4;
        mAnswerFromAgeGroupUnknown = answerFromAgeGroupUnknown;
        mAnswerFromSameLocation = answerFromSameLocation;
        mAnswerFacebookIDs = answerFacebookIDs;
    }

    public int getAnswerFromMen() {
        return mAnswerFromMen;
    }

    public void setAnswerFromMen(String answerFromMen) {
        mAnswerFromMen = Integer.parseInt(answerFromMen);
    }

    public void addAnswerCountFromMen() {
        mAnswerFromMen++;
    }

    public int getAnswerFromWomen() {
        return mAnswerFromWomen;
    }

    public void setAnswerFromWomen(String answerFromWomen) {
        mAnswerFromWomen = Integer.parseInt(answerFromWomen);
    }

    public void addAnswerCountFromWomen() {
        mAnswerFromWomen++;
    }

    public int getAnswerFromAgeGroup1() {
        return mAnswerFromAgeGroup1;
    }

    public void setAnswerFromAgeGroup1(String answerFromAgeGroup1) {
        mAnswerFromAgeGroup1 = Integer.parseInt(answerFromAgeGroup1);
    }

    public void addAnswerCountFromAgeGroup1() {
        mAnswerFromAgeGroup1++;
    }

    public int getAnswerFromAgeGroup2() {
        return mAnswerFromAgeGroup2;
    }

    public void setAnswerFromAgeGroup2(String answerFromAgeGroup2) {
        mAnswerFromAgeGroup2 = Integer.parseInt(answerFromAgeGroup2);
    }

    public void addAnswerCountFromAgeGroup2() {
        mAnswerFromAgeGroup2++;
    }

    public int getAnswerFromAgeGroup3() {
        return mAnswerFromAgeGroup3;
    }

    public void setAnswerFromAgeGroup3(String answerFromAgeGroup3) {
        mAnswerFromAgeGroup3 = Integer.parseInt(answerFromAgeGroup3);
    }

    public void addAnswerCountFromAgeGroup3() {
        mAnswerFromAgeGroup3++;
    }

    public int getAnswerFromAgeGroup4() {
        return mAnswerFromAgeGroup4;
    }

    public void setAnswerFromAgeGroup4(String answerFromAgeGroup4) {
        mAnswerFromAgeGroup4 = Integer.parseInt(answerFromAgeGroup4);
    }

    public void addAnswerCountFromAgeGroup4() {
        mAnswerFromAgeGroup4++;
    }

    public int getAnswerFromSameLocation() {
        return mAnswerFromSameLocation;
    }

    public void setAnswerFromSameLocation(String answerFromSameLocation) {
        mAnswerFromSameLocation = Integer.parseInt(answerFromSameLocation);
    }

    public void addAnswerCountFromSameLocation() {
        mAnswerFromSameLocation++;
    }

    public int getAnswerFromUnknownSex() {
        return mAnswerFromUnknownSex;
    }

    public void setAnswerFromUnknownSex(String answerFromUnknownSex) {
        mAnswerFromUnknownSex = Integer.parseInt(answerFromUnknownSex);
    }

    public int getAnswerFromAgeGroupUnknown() {
        return mAnswerFromAgeGroupUnknown;
    }

    public void setAnswerFromAgeGroupUnknown(String answerFromAgeGroupUnknown) {
        mAnswerFromAgeGroupUnknown = Integer.parseInt(answerFromAgeGroupUnknown);
    }

    public void addAnswerCountFromAgeGroupUnknown() {
        mAnswerFromAgeGroupUnknown++;
    }

    public void addAnswerCountAgeGroupUnknown() {
        mAnswerFromAgeGroupUnknown++;
    }

    public List<String> getAnswerFacebookIDs() {
        return mAnswerFacebookIDs;
    }

    public void setAnswerFacebookIDs(List<String> answerFacebookIDs) {
        mAnswerFacebookIDs = answerFacebookIDs;
    }

    public void addAnswerFacebookID(String ID) {
        mAnswerFacebookIDs.add(ID);
    }
}
