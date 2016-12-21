package com.bnnvara.kiespijn.Deadline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaFragment;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;

public class DeadlineFragment extends Fragment {

    private static final String TAG = "DeadlineFragment";
    static final String DILEMMA_OBJECT = "dilemma_object";

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
        Button postDilemmaButton = (Button) view.findViewById(R.id.button_deadline_pick_time);
        SeekBar timeBar = (SeekBar) view.findViewById(R.id.seekBar_deadline);
        final TextView timeText = (TextView) view.findViewById(R.id.textview_deadline_hours);
        Button previousButton = (Button) view.findViewById(R.id.button_previous_deadline);

        if (mDilemma.getDeadline() != 0) {

            Log.i(TAG, "CreatedAt: " + mDilemma.getDateAndTime(mDilemma.getCreatedAt()));
            Log.i(TAG, "Deadline: " + mDilemma.getDateAndTime(mDilemma.getDeadline()));

            long deadline = (mDilemma.getDeadline() - mDilemma.getCreatedAt()) * 1000L;
            long timeToAdd = deadline / 3600000;
            mDeadline = (int) timeToAdd;
            timeText.setText(getString(R.string.deadline_hours, timeToAdd));
            timeBar.setProgress((int) timeToAdd);
        } else {
            timeText.setText(getString(R.string.deadline_hours, 12));
        }


        final int stepSize = 1;
        timeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = (Math.round(progress / stepSize)) * stepSize;
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

        postDilemmaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDilemma();
                Intent i = DilemmaActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateCreatedAndDeadLine(mDeadline);
                Intent i = DilemmaFromWhoActivity.newIntent(getActivity());
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

    private void setDateCreatedAndDeadLine(int deadline) {
        mDilemma.setCreatedAt();
        mDilemma.setDeadline(deadline);
    }

    private void setUserDetails() {
        User user = User.getInstance();
        mDilemma.setCreator_fb_id(user.getUserKey());
        mDilemma.setCreator_name(user.getName());
        mDilemma.setCreator_sex(user.getSex());
        mDilemma.setCreator_age(user.getAge());
        mDilemma.setCreator_picture_url(user.getProfilePictureURL());
    }

    private void postDilemma() {
        setUserDetails();
        setDateCreatedAndDeadLine(mDeadline);
        mDilemma.setUuid();

        // SOME CODE TO POST DILEMMA TO DATABASE
        Log.i(TAG, " DILLEMMA INFORMATION");
        Log.i(TAG, " Title: " + mDilemma.getTitle());
        Log.i(TAG, " Facebook ID: " + mDilemma.getCreator_fb_id());
        Log.i(TAG, " PhotoA Title: " + mDilemma.getTitlePhotoA());
        Log.i(TAG, " PhotoA Uti: " + mDilemma.getPhotoA());
        Log.i(TAG, " PhotoB Title: " + mDilemma.getTitlePhotoB());
        Log.i(TAG, " PhotoB Uri: " + mDilemma.getPhotoB());
        Log.i(TAG, " Is To All: " + mDilemma.getIsToAll());
        Log.i(TAG, " Is Anonynous: " + mDilemma.getIsAnonymous());
        Log.i(TAG, " Created At: " + mDilemma.getDateAndTime(mDilemma.getCreatedAt()));
        Log.i(TAG, " Deadline: " + mDilemma.getDateAndTime(mDilemma.getDeadline()));

        DilemmaFragment.addDilemmaToTempList(mDilemma);

        mDilemma = null;
    }
}
