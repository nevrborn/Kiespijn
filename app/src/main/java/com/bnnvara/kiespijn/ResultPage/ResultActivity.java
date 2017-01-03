package com.bnnvara.kiespijn.ResultPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class ResultActivity extends SingleFragmentActivity {

    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        return new Intent(context, DilemmaActivity.class);
    }


    @Override
    protected Fragment createFragment() {
        return ResultFragment.newInstance();
    }
}
