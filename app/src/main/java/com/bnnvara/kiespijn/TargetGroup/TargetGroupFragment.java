package com.bnnvara.kiespijn.TargetGroup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bnnvara.kiespijn.R;

public class TargetGroupFragment extends Fragment {

    private static final String TAG = "TargetGroupFragment";

    public static TargetGroupFragment newInstance() {
        return new TargetGroupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_targetgroup, container, false);

        // Setting up the XML objects
        TextView title = (TextView) view.findViewById(R.id.textview_targetgroup_title);
        Button friendsButton = (Button) view.findViewById(R.id.button_targetgroup_friends);
        Button everyoneButton = (Button) view.findViewById(R.id.button_targetgroup_everyone);
        Button callSomeoneButton = (Button) view.findViewById(R.id.button_targetgroup_call_someone);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_extra_light_italic = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLightItalic.ttf");
        Typeface source_sans_regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
        title.setTypeface(source_sans_extra_light);
        friendsButton.setTypeface(source_sans_extra_light);
        everyoneButton.setTypeface(source_sans_extra_light);
        callSomeoneButton.setTypeface(source_sans_extra_light);


        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        everyoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        callSomeoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

}
