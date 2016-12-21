package com.bnnvara.kiespijn.TargetGroup;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.TextView;

import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.R;


public class TargetGroupFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;


    public static TargetGroupFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new TargetGroupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_targetgroup, container, false);

        // Setting up the XML objects
        TextView title = (TextView) view.findViewById(R.id.textview_targetgroup_title);
        final Button friendsButton = (Button) view.findViewById(R.id.button_targetgroup_friends);
        final Button everyoneButton = (Button) view.findViewById(R.id.button_targetgroup_everyone);
        final Button callSomeoneButton = (Button) view.findViewById(R.id.button_targetgroup_call_someone);
        Button nextButton = (Button) view.findViewById(R.id.button_next_target_group);
        Button previousButton = (Button) view.findViewById(R.id.button_previous_target_group);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_extra_light_italic = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLightItalic.ttf");
        Typeface source_sans_regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        title.setTypeface(source_sans_bold);
        friendsButton.setTypeface(source_sans_extra_light);
        everyoneButton.setTypeface(source_sans_extra_light);
        callSomeoneButton.setTypeface(source_sans_extra_light);

        if (mDilemma != null && !mDilemma.getFirstTimeToTargetGroup()) {

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
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = DilemmaFromWhoActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
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

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_no_create_button, menu);
    }


}
