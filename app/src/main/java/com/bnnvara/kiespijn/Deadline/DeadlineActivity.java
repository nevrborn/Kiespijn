package com.bnnvara.kiespijn.Deadline;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DeadlineActivity extends SingleFragmentActivity {

    private static final String TAG = "DeadlineActivity";
    private static final String DILEMMA_OBJECT = "dilemma_object";

    private Dilemma mDilemma;

    public static Intent newIntent(Context context) {
        return new Intent(context, DeadlineActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        mDilemma = dilemma;
        return DeadlineFragment.newInstance(dilemma);
    }

    @Override
    public void onBackPressed() {
        Intent i = DilemmaFromWhoActivity.newIntent(getBaseContext());
        i.putExtra(DILEMMA_OBJECT, mDilemma);
        startActivity(i);
        finish();
    }
}
