package com.bnnvara.kiespijn.TargetGroup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.SingleFragmentActivity;

public class TargetGroupActivity extends SingleFragmentActivity {

    static final String DILEMMA_OBJECT = "dilemma_object";

    public static Intent newIntent(Context context) {
        return new Intent(context, TargetGroupActivity.class);
    }

    @Override
    protected TargetGroupFragment createFragment() {
        Intent i = getIntent();
        Dilemma dilemma = (Dilemma) i.getSerializableExtra(DILEMMA_OBJECT);
        return TargetGroupFragment.newInstance(dilemma);
    }

}
