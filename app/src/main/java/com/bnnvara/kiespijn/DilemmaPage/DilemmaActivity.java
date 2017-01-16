package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.ArticleSearchPage.ArticleSearchFragment;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DilemmaActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return DilemmaFragment.newInstance();
    }

}