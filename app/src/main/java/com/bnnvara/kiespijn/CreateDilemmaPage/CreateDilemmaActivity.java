package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class CreateDilemmaActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return CreateDilemmaFragment.newInstance();
    }
}
