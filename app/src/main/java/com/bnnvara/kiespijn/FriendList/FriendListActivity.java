package com.bnnvara.kiespijn.FriendList;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class FriendListActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";

    public static Intent newIntent(Context context, Dilemma dilemma) {
        Intent i = new Intent(context, FriendListActivity.class);
        i.putExtra(DILEMMA_OBJECT, dilemma);
        return i;
    }

    @Override
    protected FriendListFragment createFragment() {
        Dilemma dilemma = (Dilemma) getIntent().getSerializableExtra(DILEMMA_OBJECT);
        return FriendListFragment.newInstance(dilemma);
    }
}
