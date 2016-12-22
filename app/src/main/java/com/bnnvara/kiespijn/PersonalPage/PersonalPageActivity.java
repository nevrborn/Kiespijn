package com.bnnvara.kiespijn.PersonalPage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.SingleFragmentActivity;

public class PersonalPageActivity extends SingleFragmentActivity {

    private static final String EXTRA_USER_FB_ID = "com.bnnvara.kiespijn.user_fb_id";

    /*
        * create Intent to start this activity
        */
    public static Intent newIntent(Context context, String userFbId) {
        Intent i = new Intent(context, PersonalPageActivity.class);
        i.putExtra(EXTRA_USER_FB_ID, userFbId);
        return i;
    }


    @Override
    protected Fragment createFragment() {
        return PersonalPageFragment.newInstance();
    }
}
