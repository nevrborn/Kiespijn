package com.bnnvara.kiespijn.DilemmaFromWho;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bnnvara.kiespijn.Dilemma.DilemmaProvider;
import com.bnnvara.kiespijn.R;


public class DilemmaFromWhoFragment extends Fragment {

    private static final String TAG = "DilemmaFromWhoFragment";

    private static DilemmaProvider mDilemmaProvider;
    private String mDilemmaKey;
    private Boolean isAnonymous;

    public static DilemmaFromWhoFragment newInstance() {
        return new DilemmaFromWhoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDilemmaProvider = DilemmaProvider.get(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from_who, container, false);

        Button anonymousButton = (Button) view.findViewById(R.id.button_fromwho_anonymous);
        Button myselfButton = (Button) view.findViewById(R.id.button_fromwho_myself);

        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAnonymous = false;
            }
        });

        myselfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAnonymous = true;
            }
        });


        return view;
    }
}
