package com.bnnvara.kiespijn.ResultPage;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.bnnvara.kiespijn.ContentPage.ContentPageActivity;
import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaFragment;
import com.bnnvara.kiespijn.Login.LoginActivity;
import com.bnnvara.kiespijn.PersonalPage.PersonalPageActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;

public class ResultFragment extends Fragment {

    // constants
    private static final String TAG = DilemmaFragment.class.getSimpleName();
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String LOGGING_OUT = "logging_out";

    // Views
    private ImageView mUserPhotoImageView;
    private ImageView mClockImageView;
    private ImageView mWinnerImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private TextView mTimeLeftTextView;
    private TextView mAnswerATextView;
    private TextView mAnswerBTextView;
    private Button mAddedContentButtonA;
    private Button mAddedContentButtonB;

    private TextRoundCornerProgressBar mTotalLeftProgressBar;
    private TextRoundCornerProgressBar mTotalRightProgressBar;
    private TextRoundCornerProgressBar mMenLeftProgressBar;
    private TextRoundCornerProgressBar mMenRightProgressBar;
    private TextRoundCornerProgressBar mWomenLeftProgressBar;
    private TextRoundCornerProgressBar mWomanRightProgressBar;
    private TextRoundCornerProgressBar mUnknownSexLeftProgressBar;
    private TextRoundCornerProgressBar mUnknownSexRightProgressBar;
    private TextRoundCornerProgressBar mCityLeftProgressBar;
    private TextRoundCornerProgressBar mCityRightProgressBar;

    private TextView mTotalNrOfResultsTextView;
    private TextView mMenNrOfResultsTextView;
    private TextView mWomenNrOfResultsTextView;
    private TextView mUnknownSexNrOfResultsTextView;
    private TextView mCityNrOfResultsTextView;

    private TextView ageGroup1NrOfResultsLeftTextView;
    private TextView ageGroup2NrOfResultsLeftTextView;
    private TextView ageGroup3NrOfResultsLeftTextView;
    private TextView ageGroup4NrOfResultsLeftTextView;
    private TextView ageGroupUnknownNrOfResultsLeftTextView;
    private TextView ageGroup1ScoreLeftTextView;
    private TextView ageGroup2ScoreLeftTextView;
    private TextView ageGroup3ScoreLeftTextView;
    private TextView ageGroup4ScoreLeftTextView;
    private TextView ageGroupUnknownScoreLeftTextView;

    private TextView ageGroup1NrOfResultsRightTextView;
    private TextView ageGroup2NrOfResultsRightTextView;
    private TextView ageGroup3NrOfResultsRightTextView;
    private TextView ageGroup4NrOfResultsRightTextView;
    private TextView ageGroupUnknownNrOfResultsRightTextView;
    private TextView ageGroup1ScoreRightTextView;
    private TextView ageGroup2ScoreRightTextView;
    private TextView ageGroup3ScoreRightTextView;
    private TextView ageGroup4ScoreRightTextView;
    private TextView ageGroupUnknownScoreRightTextView;

    private TextView contentACountTextView;
    private TextView contentBCountTextView;

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

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo_personal_page);
        mClockImageView = (ImageView) view.findViewById(R.id.image_view_clock);
        mWinnerImageView = (ImageView) view.findViewById(R.id.result_page_winner_icon);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username_personal_page);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info_personal_page);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma_personal_page);
        mTimeLeftTextView = (TextView) view.findViewById(R.id.text_view_time_left);
        mAnswerATextView = (TextView) view.findViewById(R.id.text_view_result_answer_A);
        mAnswerBTextView = (TextView) view.findViewById(R.id.text_view_result_answer_B);
        mAddedContentButtonA = (Button) view.findViewById(R.id.button_added_content_A);
        mAddedContentButtonB = (Button) view.findViewById(R.id.button_added_content_B);
        mTotalLeftProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_total_left);
        mTotalRightProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_total_right);
        mMenLeftProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_men_left);
        mMenRightProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_men_right);
        mWomenLeftProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_women_left);
        mWomanRightProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_women_right);
        mUnknownSexLeftProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_unknown_left);
        mUnknownSexRightProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_unknown_right);
        mCityLeftProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_city_left);
        mCityRightProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.progressBar_city_right);
        mTotalNrOfResultsTextView = (TextView) view.findViewById(R.id.text_view_results_total);
        mMenNrOfResultsTextView = (TextView) view.findViewById(R.id.text_view_results_men);
        mWomenNrOfResultsTextView = (TextView) view.findViewById(R.id.text_view_results_women);
        mUnknownSexNrOfResultsTextView = (TextView) view.findViewById(R.id.text_view_results_uknown_sex);
        mCityNrOfResultsTextView = (TextView) view.findViewById(R.id.text_view_results_city);
        ageGroup1NrOfResultsLeftTextView = (TextView) view.findViewById(R.id.age_group_1_nr_left_text_view);
        ageGroup2NrOfResultsLeftTextView = (TextView) view.findViewById(R.id.age_group_2_nr_left_text_view);
        ageGroup3NrOfResultsLeftTextView = (TextView) view.findViewById(R.id.age_group_3_nr_left_text_view);
        ageGroup4NrOfResultsLeftTextView = (TextView) view.findViewById(R.id.age_group_4_nr_left_text_view);
        ageGroupUnknownNrOfResultsLeftTextView = (TextView) view.findViewById(R.id.age_group_unkown_nr_left_text_view);
        ageGroup1ScoreLeftTextView = (TextView) view.findViewById(R.id.age_group_1_score_left_text_view);
        ageGroup2ScoreLeftTextView = (TextView) view.findViewById(R.id.age_group_2_score_left_text_view);
        ageGroup3ScoreLeftTextView = (TextView) view.findViewById(R.id.age_group_3_score_left_text_view);
        ageGroup4ScoreLeftTextView = (TextView) view.findViewById(R.id.age_group_4_score_left_text_view);
        ageGroupUnknownScoreLeftTextView = (TextView) view.findViewById(R.id.age_group_unknown_score_left_text_view);
        ageGroup1NrOfResultsRightTextView = (TextView) view.findViewById(R.id.age_group_1_nr_right_text_view);
        ageGroup2NrOfResultsRightTextView = (TextView) view.findViewById(R.id.age_group_2_nr_right_text_view);
        ageGroup3NrOfResultsRightTextView = (TextView) view.findViewById(R.id.age_group_3_nr_right_text_view);
        ageGroup4NrOfResultsRightTextView = (TextView) view.findViewById(R.id.age_group_4_nr_right_text_view);
        ageGroupUnknownNrOfResultsRightTextView = (TextView) view.findViewById(R.id.age_group_unknown_nr_right_text_view);
        ageGroup1ScoreRightTextView = (TextView) view.findViewById(R.id.age_group_1_score_right_text_view);
        ageGroup2ScoreRightTextView = (TextView) view.findViewById(R.id.age_group_2_score_right_text_view);
        ageGroup3ScoreRightTextView = (TextView) view.findViewById(R.id.age_group_3_score_right_text_view);
        ageGroup4ScoreRightTextView = (TextView) view.findViewById(R.id.age_group_4_score_right_text_view);
        ageGroupUnknownScoreRightTextView = (TextView) view.findViewById(R.id.age_group_unknown_score_right_text_view);
        contentACountTextView = (TextView) view.findViewById(R.id.contentNrLeftTextView);
        contentBCountTextView = (TextView) view.findViewById(R.id.contentNrRightTextView);

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

        updateUi();
        animateWinnerCrown();

        return view;
    }

    private void updateUi() {

        if (mDilemma.getCreator_hometown() == null) {
            mDilemma.setCreator_hometown("Onbekend");
        }

        // dilemma text, user etcetera
        if (mDilemma.getIsAnonymous()) {
            String ageToShow = "Leeftijd onbekend";
            if (!mDilemma.getCreator_age().equals("Leeftijd onbekend")) {
                ageToShow = mDilemma.getCreator_ageRange();
            }

            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + ageToShow + " jaar | " + mDilemma.getCreator_hometown());
            mUserNameTextView.setText(R.string.anonymous);
        } else {
            mUserDescriptionTextView.setText(
                    mDilemma.getCreator_sex() + " | " + mDilemma.getCreator_age() + " jaar | " + mDilemma.getCreator_hometown());
            mUserNameTextView.setText(mDilemma.getCreator_name());
        }
        mDilemmaTextView.setText(mDilemma.getTitle());

        // time left
        if (mDilemma.getTimeLeft() < 0) {
            mTimeLeftTextView.setText("---");
        } else if (mDilemma.getTimeLeft() == 1) {
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

        // set the content bagde numbers according to content size
        if (mDilemma.getContents() != null) {
            if (mDilemma.getContentCountA() == 0) {
                contentACountTextView.setText("0");
                mAddedContentButtonA.setClickable(false);
            } else {
                contentACountTextView.setText(Integer.toString(mDilemma.getContentCountA()));
                mAddedContentButtonA.setClickable(true);
            }

            if (mDilemma.getContentCountB() == 0) {
                contentBCountTextView.setText("0");
                mAddedContentButtonB.setClickable(false);
            } else {
                contentBCountTextView.setText(Integer.toString(mDilemma.getContentCountB()));
                mAddedContentButtonB.setClickable(true);
            }
        }

        calculateAllStatistics();
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
            case android.R.id.home:
                getActivity().onBackPressed();
            default:
                return true;
        }
    }

    private void calculateAllStatistics() {
        // Total
        mTotalNrOfResultsTextView.setText(String.valueOf(mDilemma.getTotalVotes()));
        mTotalLeftProgressBar.setProgress(mDilemma.getScoreA());
        mTotalLeftProgressBar.setProgressText(String.valueOf(mDilemma.getScoreA()) + "%");
        mTotalRightProgressBar.setProgress(mDilemma.getScoreB());
        mTotalRightProgressBar.setProgressText(String.valueOf(mDilemma.getScoreB()) + "%");

        // Men
        mMenNrOfResultsTextView.setText("Mannen (" + mDilemma.getVotesFromMen() + ")");
        mMenLeftProgressBar.setProgress(mDilemma.getScoreMenA());
        mMenLeftProgressBar.setProgressText(String.valueOf(mDilemma.getScoreMenA()) + "%");
        mMenRightProgressBar.setProgress(mDilemma.getScoreMenB());
        mMenRightProgressBar.setProgressText(String.valueOf(mDilemma.getScoreMenB()) + "%");

        // Women
        mWomenNrOfResultsTextView.setText("Vrouwen (" + mDilemma.getVotesFromWomen() + ")");
        mWomenLeftProgressBar.setProgress(mDilemma.getScoreWomenA());
        mWomenLeftProgressBar.setProgressText(String.valueOf(mDilemma.getScoreWomenA()) + "%");
        mWomanRightProgressBar.setProgress(mDilemma.getScoreWomenB());
        mWomanRightProgressBar.setProgressText(String.valueOf(mDilemma.getScoreWomenB()) + "%");

        // Sex unknown
        mUnknownSexNrOfResultsTextView.setText("Onbekend (" + mDilemma.getVotesFromUnknownSex() + ")");
        mUnknownSexLeftProgressBar.setProgress(mDilemma.getScoreUnknownSexA());
        mUnknownSexLeftProgressBar.setProgressText(String.valueOf(mDilemma.getScoreUnknownSexA()) + "%");
        mUnknownSexRightProgressBar.setProgress(mDilemma.getScoreUnknownSexB());
        mUnknownSexRightProgressBar.setProgressText(String.valueOf(mDilemma.getScoreUnknownSexB()) + "%");

        // Same City
        mCityNrOfResultsTextView.setText("Jouw stad (" + mDilemma.getVotesFromSameCity() + ")");
        mCityLeftProgressBar.setProgress(mDilemma.getVotesFromSameCityA());
        mCityLeftProgressBar.setProgressText(String.valueOf(mDilemma.getVotesFromSameCityA()) + "%");
        mCityRightProgressBar.setProgress(mDilemma.getVotesFromSameCityB());
        mCityRightProgressBar.setProgressText(String.valueOf(mDilemma.getVotesFromSameCityB()) + "%");
    }

    private void animateWinnerCrown() {
        Point size = new Point();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        int screenWidth = size.x;

        // Determine which side is the winner and gets the crown
        int offset = screenWidth / 4;
        if (mDilemma.getScoreA() >= 50) {
            offset *= -1;
        }

        // make the object go left or right
        ObjectAnimator animLeft = ObjectAnimator.ofFloat(
                mWinnerImageView, "translationX", mWinnerImageView.getLeft(), mWinnerImageView.getLeft() + offset);
        animLeft.setInterpolator(new DecelerateInterpolator());

        // Make the object 100% transparent
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(mWinnerImageView,"alpha",0.0f);
        animAlpha.setDuration(3000);

        // make the object rotate
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1500);
        rotateAnimation.setStartOffset(250);
        mWinnerImageView.startAnimation(rotateAnimation);

        AnimatorSet animSetWinner = new AnimatorSet();
        animSetWinner.play(animLeft).before(animAlpha);
        animSetWinner.setStartDelay(1500);
        animSetWinner.setDuration(3000);
        animSetWinner.start();
    }
}
