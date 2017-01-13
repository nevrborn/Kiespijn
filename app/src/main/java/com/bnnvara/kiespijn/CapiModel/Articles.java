package com.bnnvara.kiespijn.CapiModel;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulvancappelle on 12-01-17.
 */

public class Articles implements Serializable{

    @SerializedName("")
    private List<Article> mArticleList;

    public List<Article> getArticleList() {
        return mArticleList;
    }

    public void setArticleList(List<Article> articleList) {
        mArticleList = articleList;
    }
}
