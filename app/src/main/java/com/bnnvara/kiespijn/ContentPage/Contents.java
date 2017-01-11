package com.bnnvara.kiespijn.ContentPage;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contents implements Serializable {

    @SerializedName("optionA")
    private List<Content> mOptionAContent = new ArrayList<>();

    @SerializedName("optionB")
    private List<Content> mOptionBContent = new ArrayList<>();

    public List<Content> getOptionAContent() {
        return mOptionAContent;
    }

    public void setOptionAContent(List<Content> optionAContent) {
        mOptionAContent = optionAContent;
    }

    public List<Content> getOptionBContent() {
        return mOptionBContent;
    }

    public void setOptionBContent(List<Content> optionBContent) {
        mOptionBContent = optionBContent;
    }

    public void addContentToOptionA(Content content) {
        mOptionAContent.add(content);
    }

    public void addContentToOptionB(Content content) {
        mOptionBContent.add(content);
    }
}
