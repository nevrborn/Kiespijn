package com.bnnvara.kiespijn.CapiModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by paulvancappelle on 12-01-17.
 */

public class ArticleRoot implements Serializable {

    @SerializedName("_source")
    private String mArticle;


    public String getArticle() {
        return mArticle;
    }

    public void setArticle(String article) {
        mArticle = article;
    }
}
