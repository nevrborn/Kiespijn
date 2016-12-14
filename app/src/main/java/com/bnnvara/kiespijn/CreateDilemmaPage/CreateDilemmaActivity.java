package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class CreateDilemmaActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, CreateDilemmaActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        return CreateDilemmaFragment.newInstance();
    }


}
