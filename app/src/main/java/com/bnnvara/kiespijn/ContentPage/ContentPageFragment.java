package com.bnnvara.kiespijn.ContentPage;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;

public class ContentPageFragment extends Fragment {

    private static Dilemma mDilemma;
    private static String mAnswerOption;

    public static Fragment newInstance(Dilemma dilemma, String answerOption) {
        mDilemma = dilemma;
        mAnswerOption = answerOption;
        return new ContentPageFragment();
    }

}
