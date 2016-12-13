package com.bnnvara.kiespijn.DilemmaPage;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DilemmaActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return DilemmaFragment.newInstance();
    }
}
