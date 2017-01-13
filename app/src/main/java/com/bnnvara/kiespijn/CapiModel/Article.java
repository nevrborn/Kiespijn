package com.bnnvara.kiespijn.CapiModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by paulvancappelle on 13-01-17.
 */

public class Article implements Serializable {

    @SerializedName("id")
    private String mId;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
