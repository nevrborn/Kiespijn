package com.bnnvara.kiespijn.CapiModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by paulvancappelle on 12-01-17.
 */

public class Articles implements Serializable{

    @SerializedName("hits")
    private List<ArticleRoot> mArticleRootList;



    public List<ArticleRoot> getArticleRootList() {
        return mArticleRootList;
    }

    public void setArticleRootList(List<ArticleRoot> articleRootList) {
        mArticleRootList = articleRootList;
    }
}
