package com.bnnvara.kiespijn.ResultPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class ResultActivity extends SingleFragmentActivity {

    private static final String EXTRA_DILEMMA = "com.bnnvara.kiespijn.dilemma";

    /*
        * create Intent to start this activity
        */
    public static Intent newIntent(Context context, Dilemma dilemma) {
        Intent i = new Intent(context, ResultActivity.class);
        i.putExtra(EXTRA_DILEMMA, dilemma);
        return i;
    }


    @Override
    protected Fragment createFragment() {
        return ResultFragment.newInstance((Dilemma) getIntent().getSerializableExtra(EXTRA_DILEMMA));
    }
}
