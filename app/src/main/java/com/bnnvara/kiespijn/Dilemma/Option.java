package com.bnnvara.kiespijn.Dilemma;

import android.media.Image;

/**
 * Created by nevrborn on 13.12.2016.
 */

public class Option {

    private String mTitle;
    private String mText;
    private Image mImage;

    public Option(String title, String text) {
        mTitle = title;
        mText = text;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }
}
