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

    public long getTotalImages() {
        long totalImages;

        totalImages = Long.parseLong(mSearchInfo.get("totalResults"));

        return totalImages;
    }
}
