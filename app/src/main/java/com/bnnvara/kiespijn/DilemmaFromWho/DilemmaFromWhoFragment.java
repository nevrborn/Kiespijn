package com.bnnvara.kiespijn.DilemmaFromWho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bnnvara.kiespijn.Deadline.DeadlineActivity;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaProvider;
import com.bnnvara.kiespijn.R;


public class DilemmaFromWhoFragment extends Fragment {

    private static final String TAG = "DilemmaFromWhoFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;

    public static DilemmaFromWhoFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new DilemmaFromWhoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from_who, container, false);

        Button anonymousButton = (Button) view.findViewById(R.id.button_fromwho_anonymous);
        Button myselfButton = (Button) view.findViewById(R.id.button_fromwho_myself);
        Button nextButton = (Button) view.findViewById(R.id.button_next_from_who);

        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDilemma.setAnonymous("1");
            }
        });

        myselfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDilemma.setAnonymous(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = DeadlineActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_no_create_button, menu);
    }
}
