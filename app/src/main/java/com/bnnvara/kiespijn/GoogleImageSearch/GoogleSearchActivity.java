package com.bnnvara.kiespijn.GoogleImageSearch;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class GoogleSearchActivity extends SingleFragmentActivity {

    static final String SEARCH_STRING = "search_string";

    public static Intent newIntent(Context context) {
        return new Intent(context, GoogleSearchActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        String searchString = getIntent().getStringExtra(SEARCH_STRING);
        return GoogleSearchFragment.newInstance(searchString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
