package com.bnnvara.kiespijn.GoogleImageSearch;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class GoogleImageApiResponse {

    @SerializedName("items")
    private List<GalleryItem> mGalleryItems;


    public GoogleImageApiResponse() {

    }

    public List<GalleryItem> getGalleryItems() {
        return mGalleryItems;
    }

}
