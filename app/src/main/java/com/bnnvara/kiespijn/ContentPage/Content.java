package com.bnnvara.kiespijn.ContentPage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Content implements Serializable {

    @SerializedName("text")
    private String mText;

    @SerializedName("photoLink")
    private String mPhotoLink;

    @SerializedName("articleUrl")
    private String mArticleUrl;

    @SerializedName("isAPhoto")
    private Boolean mIsAPhoto;

    @SerializedName("user_fb_name")
    private String mUserFbName;

    @SerializedName("user_fb_id")
    private String mUserFbID;

    @SerializedName("user_fb_age")
    private String mUserFbAge;

    @SerializedName("user_fb_gender")
    private String mUserFbGender;

    @SerializedName("user_fb_photo_link")
    private String mUserFbPhoto;

    @SerializedName("user_fb_hometown")
    private String mUserFbHometown;

    public Content() {
    }

    public Content(String text, Boolean isAPhoto, String userFbName, String userFbID, String userFbAge, String userFbGender, String userFbPhoto, String hometown) {
        mText = text;
        mIsAPhoto = isAPhoto;
        mUserFbName = userFbName;
        mUserFbID = userFbID;
        mUserFbAge = userFbAge;
        mUserFbGender = userFbGender;
        mUserFbPhoto = userFbPhoto;
        mUserFbHometown = hometown;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getPhotoLink() {
        return mPhotoLink;
    }

    public void setPhotoLink(String photoLink) {
        mPhotoLink = photoLink;
    }

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        mArticleUrl = articleUrl;
    }

    public Boolean getAPhoto() {
        return mIsAPhoto;
    }

    public void setAPhoto(Boolean APhoto) {
        mIsAPhoto = APhoto;
    }

    public String getUserFbName() {
        return mUserFbName;
    }

    public void setUserFbName(String userFbName) {
        mUserFbName = userFbName;
    }

    public String getUserFbID() {
        return mUserFbID;
    }

    public void setUserFbID(String userFbID) {
        mUserFbID = userFbID;
    }

    public String getUserFbAge() {
        return mUserFbAge;
    }

    public void setUserFbAge(String userFbAge) {
        mUserFbAge = userFbAge;
    }

    public String getUserFbGender() {
        return mUserFbGender;
    }

    public void setUserFbGender(String userFbGender) {
        mUserFbGender = userFbGender;
    }

    public String getUserFbPhoto() {
        return mUserFbPhoto;
    }

    public void setUserFbPhoto(String userFbPhoto) {
        mUserFbPhoto = userFbPhoto;
    }

    public String getUserFbHometown() {
        return mUserFbHometown;
    }

    public void setUserFbHometown(String userFbHometown) {
        mUserFbHometown = userFbHometown;
    }
}
