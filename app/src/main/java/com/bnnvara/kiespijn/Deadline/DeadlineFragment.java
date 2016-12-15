package com.bnnvara.kiespijn.Deadline;

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
import android.widget.SeekBar;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.R;

public class DeadlineFragment extends Fragment {

    private static final String TAG = "DeadlineFragment";

    private static Dilemma mDilemma;
    private int mDeadline;

    public static DeadlineFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new DeadlineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deadline, container, false);

        final TextView title = (TextView) view.findViewById(R.id.textview_deadline_title);
        Button timeButton = (Button) view.findViewById(R.id.button_deadline_pick_time);
        SeekBar timeBar = (SeekBar) view.findViewById(R.id.seekBar_deadline);
        final TextView timeText = (TextView) view.findViewById(R.id.textview_deadline_hours);
        Button nextButton = (Button) view.findViewById(R.id.button_next_deadline);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        final int stepSize = 1;
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = ((int) Math.round(progress / stepSize)) * stepSize;
                timeText.setText(getString(R.string.deadline_hours, progress));
                title.setText(getString(R.string.deadline_title, progress));
                mDeadline = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
