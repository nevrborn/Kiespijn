package com.bnnvara.kiespijn.DilemmaFromWho;

import android.content.Context;
import android.content.Intent;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class DilemmaFromWhoActivity extends SingleFragmentActivity {

    static final String DILEMMA_OBJECT = "dilemma_object";

    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaFromWhoActivity.class);
    }

    @Override
    protected DilemmaFromWhoFragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        return DilemmaFromWhoFragment.newInstance(dilemma);
    }
}
