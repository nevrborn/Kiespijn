package com.bnnvara.kiespijn.GroupPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class GroupPageActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, GroupPageActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return GroupPageFragment.newInstance();
    }
}
