package com.bnnvara.kiespijn.GoogleImageSearch;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;


public class GalleryItem {

    @SerializedName("title")
    private String mTitle;
    @SerializedName("link")
    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url_s) {
        mUrl = url_s;
    }

}
