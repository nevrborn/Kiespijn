package com.bnnvara.kiespijn.ArticleSearchPage;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class ArticleSearchActivity extends SingleFragmentActivity {

    private static final String SEARCH_STRING = "search_string";

    public static Intent newIntent(Context context) {
        return new Intent(context, ArticleSearchActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        String searchString = getIntent().getStringExtra(SEARCH_STRING);
        return ArticleSearchFragment.newInstance(searchString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
