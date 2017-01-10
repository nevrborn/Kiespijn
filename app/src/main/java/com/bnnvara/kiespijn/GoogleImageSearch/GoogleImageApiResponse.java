package com.bnnvara.kiespijn.GoogleImageSearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

class GoogleImageApiResponse {

    @SerializedName("items")
    private List<GalleryItem> mGalleryItems;

    @SerializedName("searchInformation")
    private Map<String, String> mSearchInfo;

    public GoogleImageApiResponse() {

    }

    List<GalleryItem> getGalleryItems() {
        return mGalleryItems;
    }

    public Map<String, String> getSearchInfo() {
        return mSearchInfo;
    }

    public int getTotalImages() {
        int totalImages;

        totalImages = Integer.parseInt(mSearchInfo.get("totalResults"));

        return totalImages;
    }
}
