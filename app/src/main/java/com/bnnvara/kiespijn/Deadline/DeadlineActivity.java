package com.bnnvara.kiespijn.Deadline;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DeadlineActivity extends SingleFragmentActivity {

    private static final String TAG = "DeadlineActivity";
    static final String DILEMMA_OBJECT = "dilemma_object";

    public static Intent newIntent(Context context) {
        return new Intent(context, DeadlineActivity.class);
    }

    @Override
    protected DeadlineFragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        return DeadlineFragment.newInstance(dilemma);
    }
}
