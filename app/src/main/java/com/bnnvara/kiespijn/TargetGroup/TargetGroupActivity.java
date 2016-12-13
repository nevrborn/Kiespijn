package com.bnnvara.kiespijn.TargetGroup;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class TargetGroupActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, TargetGroupActivity.class);
    }

    @Override
    protected TargetGroupFragment createFragment() {
        return TargetGroupFragment.newInstance();
    }

}
