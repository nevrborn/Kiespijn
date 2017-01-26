package com.bnnvara.kiespijn.FriendList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;

public class FriendListActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";
    private Dilemma mDilemma;

    public static Intent newIntent(Context context, Dilemma dilemma) {
        Intent i = new Intent(context, FriendListActivity.class);
        i.putExtra(DILEMMA_OBJECT, dilemma);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        Dilemma dilemma = (Dilemma) getIntent().getSerializableExtra(DILEMMA_OBJECT);
        mDilemma = dilemma;
        return FriendListFragment.newInstance(dilemma);
    }

    @Override
    public void onBackPressed() {
        Intent i = TargetGroupActivity.newIntent(getBaseContext());
        i.putExtra(DILEMMA_OBJECT, mDilemma);
        startActivity(i);
        finish();
    }
}
