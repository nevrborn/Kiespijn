package com.bnnvara.kiespijn.TargetGroup;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class TargetGroupFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    private static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;
    private Button callSomeoneButton;
    private Boolean isHasChosen = false;
    private int mCallerIndex;

    private static List<Integer> mListOfCallers = new ArrayList<>();
    private static List<String> mListOfGifs = new ArrayList<>();


    public static TargetGroupFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new TargetGroupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        updateLists();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_targetgroup, container, false);

        // Setting up the XML objects
        TextView title = (TextView) view.findViewById(R.id.textview_targetgroup_title);
        final Button friendsButton = (Button) view.findViewById(R.id.button_targetgroup_friends);
        callSomeoneButton = (Button) view.findViewById(R.id.button_targetgroup_call_someone);
        final Button everyoneButton = (Button) view.findViewById(R.id.button_targetgroup_everyone);
        Button nextButton = (Button) view.findViewById(R.id.button_next_target_group);
        Button previousButton = (Button) view.findViewById(R.id.button_previous_target_group);
        final ImageView gifView = (ImageView) view.findViewById(R.id.targetgroup_gif);

        gifView.setVisibility(View.GONE);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        title.setTypeface(source_sans_bold);
        friendsButton.setTypeface(source_sans_extra_light);
        everyoneButton.setTypeface(source_sans_extra_light);
        callSomeoneButton.setTypeface(source_sans_extra_light);

        setRandomCaller();


        if (mDilemma != null && !mDilemma.getFirstTimeToTargetGroup()) {

            isHasChosen = true;

            if (!mDilemma.getIsToAll()) {
                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                friendsButton.setTextColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
            } else if (mDilemma.getIsToAll()) {
                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                friendsButton.setTextColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
            }

        }

        // set the listeners
        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                friendsButton.setTextColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorYellow));

                mDilemma.setIsToAll("false");
                mDilemma.setFirstTimeToTargetGroup(false);
                isHasChosen = true;


            }
        });

        everyoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                friendsButton.setTextColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorYellow));

                mDilemma.setIsToAll("true");
                mDilemma.setFirstTimeToTargetGroup(false);
                isHasChosen = true;
            }
        });

        callSomeoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                friendsButton.setTextColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorGreen));

                gifView.setVisibility(View.VISIBLE);

                Glide.with(getActivity())
                        .load(getRandomGifURL())
                        .asGif()
                        .into(gifView);

                friendsButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                friendsButton.setTextColor(getResources().getColor(R.color.colorYellow));
                everyoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                everyoneButton.setTextColor(getResources().getColor(R.color.colorYellow));
                callSomeoneButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                callSomeoneButton.setTextColor(getResources().getColor(R.color.colorYellow));


            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHasChosen) {
                    Intent i = DilemmaFromWhoActivity.newIntent(getActivity());
                    i.putExtra(DILEMMA_OBJECT, mDilemma);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), R.string.not_all_fields_filled, Toast.LENGTH_SHORT).show();
                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CreateDilemmaActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
                getActivity().finish();
            }
        });

        gifView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gifView.setVisibility(View.GONE);
                setRandomCaller();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_no_create_button, menu);
    }

    private void setRandomCaller() {
        mCallerIndex = 1 + (int) (Math.random() * ((13 - 1)));
        callSomeoneButton.setText(mListOfCallers.get(mCallerIndex));
    }

    private String getRandomGifURL() {
        int randomIndex = 1 + (int) (Math.random() * ((35 - 1)));
        return mListOfGifs.get(randomIndex);
    }

    public void updateLists() {
        mListOfCallers.add(R.string.targetgroup_call_someone);
        mListOfCallers.add(R.string.targetgroup_call_someone_2);
        mListOfCallers.add(R.string.targetgroup_call_someone_3);
        mListOfCallers.add(R.string.targetgroup_call_someone_4);
        mListOfCallers.add(R.string.targetgroup_call_someone_5);
        mListOfCallers.add(R.string.targetgroup_call_someone_6);
        mListOfCallers.add(R.string.targetgroup_call_someone_7);
        mListOfCallers.add(R.string.targetgroup_call_someone_8);
        mListOfCallers.add(R.string.targetgroup_call_someone_9);
        mListOfCallers.add(R.string.targetgroup_call_someone_10);
        mListOfCallers.add(R.string.targetgroup_call_someone_11);
        mListOfCallers.add(R.string.targetgroup_call_someone_12);
        mListOfCallers.add(R.string.targetgroup_call_someone_13);

        mListOfGifs.add("http://i.imgur.com/XVLXwM7.gif");
        mListOfGifs.add("https://i.imgur.com/lVlPvCB.gif");
        mListOfGifs.add("https://i.imgur.com/NMEguFf.gif");
        mListOfGifs.add("https://i.imgur.com/m3welo2.gif");
        mListOfGifs.add("https://i.imgur.com/CbRpymD.gif");
        mListOfGifs.add("https://i.imgur.com/aLEjgN7.gif");
        mListOfGifs.add("https://i.imgur.com/ujqkMEH.gif");
        mListOfGifs.add("https://i.imgur.com/IlhEGbl.gif");
        mListOfGifs.add("https://i.imgur.com/rZgwuld.gif");
        mListOfGifs.add("https://i.imgur.com/JfDYRKv.gif");
        mListOfGifs.add("https://i.imgur.com/0glopxW.gif");
        mListOfGifs.add("https://i.imgur.com/rR8mwEL.gif");
        mListOfGifs.add("https://i.imgur.com/Dgo1nxQ.gif");
        mListOfGifs.add("https://i.imgur.com/QjbIiBA.gif");
        mListOfGifs.add("https://i.imgur.com/baCcvq8.gif");
        mListOfGifs.add("https://i.imgur.com/eEhpf79.gif");
        mListOfGifs.add("https://i.imgur.com/yeSjKv2.gif");
        mListOfGifs.add("https://i.imgur.com/AOBXHOO.gif");
        mListOfGifs.add("https://i.imgur.com/SxuKpa8.gif");
        mListOfGifs.add("https://i.imgur.com/tEUlfWt.gif");
        mListOfGifs.add("https://i.imgur.com/5wkR8Sj.gif");
        mListOfGifs.add("https://i.imgur.com/DBtFXw2.gif");
        mListOfGifs.add("https://i.imgur.com/NTtD2QI.gif");
        mListOfGifs.add("https://i.imgur.com/3wcLG9u.gif");
        mListOfGifs.add("https://i.imgur.com/iZA2t5H.gif");
        mListOfGifs.add("https://i.imgur.com/cGIBrMa.gif");
        mListOfGifs.add("http://i.imgur.com/XR1DhfB.gif");
        mListOfGifs.add("https://media.giphy.com/media/eYgVBZKw2LG6Y/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/LZElUsjl1Bu6c/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/QKKV7KFrG9XMY/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/JltOMwYmi0VrO/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/r1HGFou3mUwMw/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/F8bo1LU5U5ApG/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/QtG5CHQMMkyn6/giphy.gif");
        mListOfGifs.add("https://media.giphy.com/media/8yeOM7vQVmoBa/giphy.gif");
    }


}
