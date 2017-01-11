package com.bnnvara.kiespijn.Dilemma;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Answer implements Serializable {

    @SerializedName("answer_from_men")
    private String mAnswerFromMen;

    @SerializedName("answer_from_women")
    private String mAnswerFromWomen;

    @SerializedName("answer_from_agegroup_1")
    private String mAnswerFromAgeGroup1;

    @SerializedName("answer_from_agegroup_2")
    private String mAnswerFromAgeGroup2;

    @SerializedName("answer_from_agegroup_3")
    private String mAnswerFromAgeGroup3;

    @SerializedName("answer_from_same_location")
    private String mAnswerFromSameLocation;

    @SerializedName("answer_facebook_id_list")
    private List<String> mAnswerFacebookIDs;

    public Answer() {
    }

    public Answer(String answerFromMen, String answerFromWomen, String answerFromAgeGroup1, String answerFromAgeGroup2, String answerFromAgeGroup3, String answerFromSameLocation, List<String> answerFacebookIDs) {
        mAnswerFromMen = answerFromMen;
        mAnswerFromWomen = answerFromWomen;
        mAnswerFromAgeGroup1 = answerFromAgeGroup1;
        mAnswerFromAgeGroup2 = answerFromAgeGroup2;
        mAnswerFromAgeGroup3 = answerFromAgeGroup3;
        mAnswerFromSameLocation = answerFromSameLocation;
        mAnswerFacebookIDs = answerFacebookIDs;
    }

    public String getAnswerFromMen() {
        return mAnswerFromMen;
    }

    public void setAnswerFromMen(String answerFromMen) {
        mAnswerFromMen = answerFromMen;
    }

    public void addAnswerCountFromMen() {
        int answer = Integer.parseInt(getAnswerFromMen());
        answer += 1;
        setAnswerFromMen(Integer.toString(answer));
    }

    public String getAnswerFromWomen() {
        return mAnswerFromWomen;
    }

    public void setAnswerFromWomen(String answerFromWomen) {
        mAnswerFromWomen = answerFromWomen;
    }

    public void addAnswerCountFromWomen() {
        int answer = Integer.parseInt(getAnswerFromWomen());
        answer += 1;
        setAnswerFromWomen(Integer.toString(answer));
    }

    public String getAnswerFromAgeGroup1() {
        return mAnswerFromAgeGroup1;
    }

    public void setAnswerFromAgeGroup1(String answerFromAgeGroup1) {
        mAnswerFromAgeGroup1 = answerFromAgeGroup1;
    }

    public void addAnswerCountFromAgeGroup1() {
        int answer = Integer.parseInt(getAnswerFromAgeGroup1());
        answer += 1;
        setAnswerFromAgeGroup1(Integer.toString(answer));
    }

    public String getAnswerFromAgeGroup2() {
        return mAnswerFromAgeGroup2;
    }

    public void setAnswerFromAgeGroup2(String answerFromAgeGroup2) {
        mAnswerFromAgeGroup2 = answerFromAgeGroup2;
    }

    public void addAnswerCountFromAgeGroup2() {
        int answer = Integer.parseInt(getAnswerFromAgeGroup2());
        answer += 1;
        setAnswerFromAgeGroup2(Integer.toString(answer));
    }

    public String getAnswerFromAgeGroup3() {
        return mAnswerFromAgeGroup3;
    }

    public void setAnswerFromAgeGroup3(String answerFromAgeGroup3) {
        mAnswerFromAgeGroup3 = answerFromAgeGroup3;
    }

    public void addAnswerCountFromAgeGroup3() {
        int answer = Integer.parseInt(getAnswerFromAgeGroup3());
        answer += 1;
        setAnswerFromAgeGroup3(Integer.toString(answer));
    }

    public String getAnswerFromSameLocation() {
        return mAnswerFromSameLocation;
    }

    public void setAnswerFromSameLocation(String answerFromSameLocation) {
        mAnswerFromSameLocation = answerFromSameLocation;
    }

    public void addAnswerCountFromSameLocation() {
        int answer = Integer.parseInt(getAnswerFromSameLocation());
        answer += 1;
        setAnswerFromSameLocation(Integer.toString(answer));
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
