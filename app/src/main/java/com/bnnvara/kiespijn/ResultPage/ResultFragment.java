package com.bnnvara.kiespijn.ResultPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bnnvara.kiespijn.ContentPage.ContentPageActivity;
import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaFragment;
import com.bnnvara.kiespijn.Login.LoginActivity;
import com.bnnvara.kiespijn.PersonalPage.PersonalPageActivity;
import com.bnnvara.kiespijn.R;
import com.bumptech.glide.Glide;

public class ResultFragment extends Fragment {

    // constants
    private static final String TAG = DilemmaFragment.class.getSimpleName();
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String LOGGING_OUT = "logging_out";

    // Views
    private ImageView mUserPhotoImageView;
    private ImageView mClockImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private TextView mTimeLeftTextView;
    private TextView mAnswerATextView;
    private TextView mAnswerBTextView;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarTotalLeft;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarTotalRight;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarMenLeft;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarMenRight;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarWomenLeft;
    private com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar mProgressBarWomenRight;
    private Button mAddedContentButtonA;
    private Button mAddedContentButtonB;

    // Regular variables
    private Dilemma mDilemma;


    public static Fragment newInstance(Dilemma dilemma) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DILEMMA_OBJECT, dilemma);
        ResultFragment resultFragment = new ResultFragment();
        resultFragment.setArguments(bundle);
        return resultFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDilemma = (Dilemma) getArguments().getSerializable(DILEMMA_OBJECT);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_page, container, false);

        mDilemma = (Dilemma) getArguments().getSerializable(DILEMMA_OBJECT);

//        RoundCornerProgressBar progressBar1 = (RoundCornerProgressBar) view.findViewById(R.id.progress_1);
//        progressBar1.setProgressColor(Color.parseColor("#ed3b27"));
//        progressBar1.setProgressBackgroundColor(Color.parseColor("#808080"));
//        progressBar1.setMax(100);
//        progressBar1.setProgress(45);
//        progressBar1.setReverse(true);
//        progressBar1.setRadius(2);

//        int progressColor1 = progressBar1.getProgressColor();
//        int backgroundColor1 = progressBar1.getProgressBackgroundColor();
//        float max1 = progressBar1.getMax();
//        float progress1 = progressBar1.getProgress();

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo_personal_page);
        mClockImageView = (ImageView) view.findViewById(R.id.image_view_clock);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username_personal_page);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info_personal_page);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma_personal_page);
        mTimeLeftTextView = (TextView) view.findViewById(R.id.text_view_time_left);
        mAnswerATextView = (TextView) view.findViewById(R.id.text_view_result_answer_A);
        mAnswerBTextView = (TextView) view.findViewById(R.id.text_view_result_answer_B);
        mAddedContentButtonA = (Button) view.findViewById(R.id.button_added_content_A);
        mAddedContentButtonB = (Button) view.findViewById(R.id.button_added_content_B);
        mProgressBarTotalLeft = (com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar) view.findViewById(R.id.progressBar_total_left);
//        mProgressBarTotalRight = (com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar) view.findViewById(R.id.progressBar_total_right);
        mProgressBarMenLeft = (com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar) view.findViewById(R.id.progressBar_men_left);
//        mProgressBarMenRight = (SeekBar) view.findViewById(R.id.seekBar_men_right);
//        mProgressBarWomenLeft = (SeekBar) view.findViewById(R.id.seekBar_women_left);
//        mProgressBarWomenRight = (SeekBar) view.findViewById(R.id.seekBar_women_right);

        // set up the listeners
        mAddedContentButtonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentPageActivity.newIntent(getContext(), mDilemma, "A"));
                startActivity(intent);
            }
        });
        mAddedContentButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContentPageActivity.newIntent(getContext(), mDilemma, "B"));
                startActivity(intent);
            }
        });
//        mProgressBarTotalLeft.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
//        mProgressBarTotalRight.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
//        mProgressBarMenLeft.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
//        mProgressBarMenRight.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
//        mProgressBarWomenLeft.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
//        mProgressBarWomenRight.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });

        updateUi();
        return view;
    }

    private void updateUi() {

        // dilemma text, user etcetera
        if (mDilemma.getIsAnonymous()) {
            String ageToShow = "Leeftijd onbekend";
            if (!mDilemma.getCreator_age().equals("Leeftijd onbekend")) {
                ageToShow = mDilemma.getCreator_ageRange();
            }

            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + ageToShow);
            mUserNameTextView.setText(R.string.anonymous);
        } else {
            mUserDescriptionTextView.setText(
                    mDilemma.getCreator_sex() + " | " + mDilemma.getCreator_age());
            mUserNameTextView.setText(mDilemma.getCreator_name());
        }
        mDilemmaTextView.setText(mDilemma.getTitle());

        // time left
        if (mDilemma.getTimeLeft() < 0){
            mClockImageView.setImageResource(R.drawable.ic_clock_expired);
            mTimeLeftTextView.setText("---");
            mTimeLeftTextView.setTextColor(getResources().getColor(R.color.colorRed));
        } else if (mDilemma.getTimeLeft() == 1){
            mTimeLeftTextView.setText(R.string.time_left_1_hour);
        } else {
            mTimeLeftTextView.setText(mDilemma.getTimeLeft() + " uren");
        }

        // set user profile picture
        if (mDilemma.getCreator_picture_url() != null && !mDilemma.getIsAnonymous()) {
            Glide.with(getActivity())
                    .load(mDilemma.getCreator_picture_url())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(mUserPhotoImageView);
        } else if (mDilemma.getIsAnonymous() || mDilemma.getCreator_picture_url() == null) {
            mUserPhotoImageView.setImageResource(R.drawable.ic_action_user_photo);
        }

        // text of the two options
        mAnswerATextView.setText(mDilemma.getTitlePhotoA());
        mAnswerBTextView.setText(mDilemma.getTitlePhotoB());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_general, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_user:
                Intent intent1 = PersonalPageActivity.newIntent(getActivity());
                startActivity(intent1);
                return true;
            case R.id.menu_item_login:
                Intent intent2 = LoginActivity.newIntent(getActivity());
                intent2.putExtra(LOGGING_OUT, true);
                startActivity(intent2);
                return true;
            case R.id.menu_item_create_dilemma:
                Dilemma dilemma = new Dilemma();
                Intent intent3 = CreateDilemmaActivity.newIntent(getActivity());
                intent3.putExtra(DILEMMA_OBJECT, dilemma);
                startActivity(intent3);
                return true;
            case R.id.home:
                getActivity().onBackPressed();
            default:
                return true;
        }
    }
}
