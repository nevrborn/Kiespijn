package com.bnnvara.kiespijn.DecisionPage;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DecisionPageActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return DecisionPageFragment.newInstance();
    }
}
