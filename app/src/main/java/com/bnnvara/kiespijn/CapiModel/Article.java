package com.bnnvara.kiespijn.CapiModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by paulvancappelle on 12-01-17.
 */

public class Article implements Serializable {

    @SerializedName("uuid")
    private String mUuid;



    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }
}
