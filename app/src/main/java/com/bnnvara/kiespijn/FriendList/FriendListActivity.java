package com.bnnvara.kiespijn.FriendList;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class FriendListActivity extends SingleFragmentActivity {

    static final String DILEMMA_OBJECT = "dilemma_object";

    public static Intent newIntent(Context context) {
        return new Intent(context, FriendListActivity.class);
    }

    @Override
    protected FriendListFragement createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        return FriendListFragement.newInstance(dilemma);
    }
}
