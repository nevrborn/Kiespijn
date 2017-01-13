package com.bnnvara.kiespijn.CapiModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CapiApiResponse  implements Serializable {

    @SerializedName("")
    private Articles mArticles;

    public CapiApiResponse() {

    }

    public List<Article> getArticleList() {
        return mArticles.getArticleList();
    }
}
