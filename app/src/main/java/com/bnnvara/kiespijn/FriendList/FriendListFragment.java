package com.bnnvara.kiespijn.FriendList;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;

public class FriendListFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;

    public static FriendListFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new FriendListFragment();
    }

}
