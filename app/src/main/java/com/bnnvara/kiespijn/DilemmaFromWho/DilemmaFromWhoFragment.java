package com.bnnvara.kiespijn.DilemmaFromWho;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.Deadline.DeadlineActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;


public class DilemmaFromWhoFragment extends Fragment {

    private static final String TAG = "DilemmaFromWhoFragment";
    private static final String DILEMMA_OBJECT = "dilemma_object";

    private static Dilemma mDilemma;
    private Boolean isHasChosen = false;

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

        TextView title = (TextView) view.findViewById(R.id.textview_fromwho_title);
        final Button anonymousButton = (Button) view.findViewById(R.id.button_fromwho_anonymous);
        final Button myselfButton = (Button) view.findViewById(R.id.button_fromwho_myself);
        Button nextButton = (Button) view.findViewById(R.id.button_next_fromwho);
        Button previousButton = (Button) view.findViewById(R.id.button_previous_fromwho);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        title.setTypeface(source_sans_bold);
        anonymousButton.setTypeface(source_sans_extra_light);
        myselfButton.setTypeface(source_sans_extra_light);

        if (mDilemma != null && !mDilemma.getFirstTimeToFromWho()) {

            isHasChosen = true;

            if (mDilemma.getIsAnonymous()) {
                anonymousButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                anonymousButton.setTextColor(getResources().getColor(R.color.colorGreen));
                myselfButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                myselfButton.setTextColor(getResources().getColor(R.color.colorYellow));
            } else if (!mDilemma.getIsAnonymous()) {
                anonymousButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                anonymousButton.setTextColor(getResources().getColor(R.color.colorYellow));
                myselfButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                myselfButton.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        }

        anonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDilemma.setIsAnonymous("true");
                mDilemma.setFirstTimeToFromWho(false);
                isHasChosen = true;

                anonymousButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                anonymousButton.setTextColor(getResources().getColor(R.color.colorGreen));
                myselfButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                myselfButton.setTextColor(getResources().getColor(R.color.colorYellow));
            }
        });

        myselfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDilemma.setIsAnonymous("false");
                mDilemma.setFirstTimeToFromWho(false);
                isHasChosen = true;

                anonymousButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                anonymousButton.setTextColor(getResources().getColor(R.color.colorYellow));
                myselfButton.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                myselfButton.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHasChosen) {
                    Intent i = DeadlineActivity.newIntent(getActivity());
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
                Intent i = TargetGroupActivity.newIntent(getActivity());
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
