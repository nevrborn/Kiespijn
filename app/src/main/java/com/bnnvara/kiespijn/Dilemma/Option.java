package com.bnnvara.kiespijn.Dilemma;

import android.graphics.Bitmap;
import android.media.Image;

public class Option {

    private String mTitle;
    private String mText;
    private Bitmap mImage;

    public Option() {
        // empty constructur
    }

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

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }
}
