package com.bnnvara.kiespijn.ArticleSearchPage;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class ArticleSearchActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ArticleSearchActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return ArticleSearchFragment.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
