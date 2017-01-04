package com.bnnvara.kiespijn.GoogleImageSearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GalleryItems {

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
