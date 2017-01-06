package com.bnnvara.kiespijn.GoogleImageSearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class GoogleImageApiResponse {

    @SerializedName("items")
    private List<GalleryItem> mGalleryItems;

    public GoogleImageApiResponse() {

    }

    List<GalleryItem> getGalleryItems() {
        return mGalleryItems;
    }

}
