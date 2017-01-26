package com.bnnvara.kiespijn.TargetGroup;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class TargetGroupActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";
    private Dilemma mDilemma;

    public static Intent newIntent(Context context) {
        return new Intent(context, TargetGroupActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        mDilemma = dilemma;
        return TargetGroupFragment.newInstance(dilemma);
    }

    @Override
    public void onBackPressed() {
        Intent i = CreateDilemmaActivity.newIntent(getBaseContext());
        i.putExtra(DILEMMA_OBJECT, mDilemma);
        startActivity(i);
        finish();
    }

}
