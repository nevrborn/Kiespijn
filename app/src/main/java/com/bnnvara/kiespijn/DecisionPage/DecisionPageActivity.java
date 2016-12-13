package com.bnnvara.kiespijn.DecisionPage;

import com.bnnvara.kiespijn.SingleFragmentActivity;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupFragment;

public class DecisionPageActivity extends SingleFragmentActivity {

    @Override
    protected TargetGroupFragment createFragment() {
        return DecisionPageFragment.newInstance();
    }
}
