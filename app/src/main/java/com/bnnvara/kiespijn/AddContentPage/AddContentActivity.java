package com.bnnvara.kiespijn.AddContentPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class AddContentActivity extends SingleFragmentActivity {

    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String DILEMMA_ANSWER_ADD_CONTENT = "answer_add_content";

    public static Intent newIntent(Context context) {
        return new Intent(context, AddContentActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        Dilemma dilemma = (Dilemma) getIntent().getSerializableExtra(DILEMMA_OBJECT);
        String answerOption = getIntent().getStringExtra(DILEMMA_ANSWER_ADD_CONTENT);
        return AddContentFragment.newInstance(dilemma, answerOption);
    }


}
