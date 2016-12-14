package com.bnnvara.kiespijn.DilemmaFromWho;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.SingleFragmentActivity;

/**
 * Created by nevrborn on 14.12.2016.
 */

public class DilemmaFromWhoActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaFromWhoActivity.class);
    }

    @Override
    protected DilemmaFromWhoFragment createFragment() {
        return DilemmaFromWhoFragment.newInstance();
    }
}
