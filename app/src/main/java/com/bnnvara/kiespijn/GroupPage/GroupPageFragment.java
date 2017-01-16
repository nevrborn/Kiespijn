package com.bnnvara.kiespijn.GroupPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bnnvara.kiespijn.User;

public class GroupPageFragment extends Fragment {

    public static Fragment newInstance() {
        return new GroupPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = User.getInstance();
    }
}
