package com.bnnvara.kiespijn.FriendList;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;

public class FriendListFragement extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;

    public static FriendListFragement newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new FriendListFragement();
    }

}
