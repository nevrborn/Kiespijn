package com.bnnvara.kiespijn.DilemmaPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnnvara.kiespijn.R;


/**
 *
 */
public class DecisionPageFragment extends Fragment {


    public static Fragment newInstance() {
        return new DecisionPageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decision_page, container, false);

        // set up the references


        // set up the listeners


        return view;
    }
}