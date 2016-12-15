package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.Dilemma.Answer;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaList;
import com.bnnvara.kiespijn.Dilemma.Replies;
import com.bnnvara.kiespijn.SingleFragmentActivity;

import java.util.ArrayList;
import java.util.List;

public class DilemmaActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return DilemmaFragment.newInstance();
    }

}