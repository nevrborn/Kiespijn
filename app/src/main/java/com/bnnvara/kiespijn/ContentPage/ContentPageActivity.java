package com.bnnvara.kiespijn.ContentPage;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class ContentPageActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String DILEMMA_OPTION = "dilemma_option";

    public static Intent newIntent(Context context, Dilemma dilemma, String option) {
        Intent i = new Intent(context, ContentPageActivity.class);
        i.putExtra(DILEMMA_OBJECT, dilemma);
        i.putExtra(DILEMMA_OPTION, option);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        Dilemma dilemma = (Dilemma) getIntent().getSerializableExtra(DILEMMA_OBJECT);
        String answerOption = getIntent().getStringExtra(DILEMMA_OPTION);
        return ContentPageFragment.newInstance(dilemma, answerOption);
    }
}
