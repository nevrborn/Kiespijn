package com.bnnvara.kiespijn.Deadline;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DeadlineActivity extends SingleFragmentActivity {

    private static final String TAG = "DeadlineActivity";

    public static Intent newIntent(Context context) {
        return new Intent(context, DeadlineActivity.class);
    }

    @Override
    protected DeadlineFragment createFragment() {
        return DeadlineFragment.newInstance();
    }
}
