package com.bnnvara.kiespijn.PersonalPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class PersonalPageActivity extends SingleFragmentActivity {


    /*
    * create Intent to start this activity
    */
    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, PersonalPageActivity.class);
        return i;
    }


    @Override
    protected Fragment createFragment() {
        return PersonalPageFragment.newInstance();
    }
}
