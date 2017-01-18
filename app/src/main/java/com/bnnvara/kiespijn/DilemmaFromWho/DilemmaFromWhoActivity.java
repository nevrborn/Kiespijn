package com.bnnvara.kiespijn.DilemmaFromWho;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.ArticleSearchPage.ArticleSearchFragment;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.FriendList.FriendListActivity;
import com.bnnvara.kiespijn.SingleFragmentActivity;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;

public class DilemmaFromWhoActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";
    private Dilemma mDilemma;

    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaFromWhoActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        mDilemma = dilemma;
        return DilemmaFromWhoFragment.newInstance(dilemma);
    }

    @Override
    public void onBackPressed() {
        if (mDilemma.getIsToAll()) {
            Intent i = TargetGroupActivity.newIntent(getBaseContext());
            i.putExtra(DILEMMA_OBJECT, mDilemma);
            startActivity(i);
            finish();
        } else if (!mDilemma.getIsToAll()) {
            Intent i = FriendListActivity.newIntent(getBaseContext(), mDilemma);
            startActivity(i);
            finish();
        }
    }
}
