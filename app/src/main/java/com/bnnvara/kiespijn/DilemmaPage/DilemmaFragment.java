package com.bnnvara.kiespijn.DilemmaPage;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnnvara.kiespijn.AddContentPage.AddContentActivity;
import com.bnnvara.kiespijn.ApiEndpointInterface;
import com.bnnvara.kiespijn.ContentPage.Content;
import com.bnnvara.kiespijn.ContentPage.Contents;
import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Answer;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.Dilemma.Replies;
import com.bnnvara.kiespijn.Login.LoginActivity;
import com.bnnvara.kiespijn.PersonalPage.PersonalPageActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Primitives;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DilemmaFragment extends Fragment {

    // constants
    private static final String TAG = DilemmaFragment.class.getSimpleName();
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String LOGGING_OUT = "logging_out";
    private static final String DILEMMA_ANSWER_ADD_CONTENT = "answer_add_content";
    private static final String CURRENT_INDEX = "current_index";
    private static final String USER_ID = "current_user_id";

    // Views
    private ImageView mUserPhotoImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private LinearLayout mNoDilemmasTextView;
    private ImageView mBackgroundInfoImageView;
    private Button mSkipDilemma;
    private ImageView mFriendIcon;

    private ImageView mFirstDilemmaImage;
    private ImageView mSecondDilemmaImage;
    private TextView mFirstDilemmaImageText;
    private TextView mSecondDilemmaImageText;
    private TextView mChooseDilemmaText1;
    private TextView mChooseDilemmaText2;

    private SwipeLayout swipeLayout1;
    private SwipeLayout swipeLayout2;

    private static List<Dilemma> mDilemmaList;
    private static List<Dilemma> mTempDilemmaList = new ArrayList<>();
    private Dilemma mDilemma;
    private int mCurrentIndex;
    private String mUserFbId;
    private Boolean mFilterFriends = false;

    private float mImageAlpha = 1.0f;
    private int tempOffset;
    private Boolean hasChosen = false;

    public static Fragment newInstance() {
        return new DilemmaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX);
            mUserFbId = savedInstanceState.getString(USER_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dilemma_page, container, false);

        mUserFbId = User.getInstance().getUserKey();

        getData();

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma);
        mNoDilemmasTextView = (LinearLayout) view.findViewById(R.id.linear_layout_no_dilemmas);
        mBackgroundInfoImageView = (ImageView) view.findViewById(R.id.image_view_background_info);
        final SwitchCompat filterSwitch = (SwitchCompat) view.findViewById(R.id.dilemma_filter_switch);
        mSkipDilemma = (Button) view.findViewById(R.id.button_skip_dilemma);
        mFriendIcon = (ImageView) view.findViewById(R.id.imageview_friends);

        filterSwitch.setChecked(false);

        mFirstDilemmaImage = (ImageView) view.findViewById(R.id.image_view_first_option_decicision_page);
        mSecondDilemmaImage = (ImageView) view.findViewById(R.id.image_view_second_option_decicision_page);
        mFirstDilemmaImageText = (TextView) view.findViewById(R.id.text_view_first_image_title);
        mSecondDilemmaImageText = (TextView) view.findViewById(R.id.text_view_second_image_title);

        mChooseDilemmaText1 = (TextView) view.findViewById(R.id.textview_next_dilemma_first);
        mChooseDilemmaText2 = (TextView) view.findViewById(R.id.textview_next_dilemma_second);
        final TextView addContent1 = (TextView) view.findViewById(R.id.textview_add_content_swipe_first);
        final TextView addContent2 = (TextView) view.findViewById(R.id.textview_add_content_swipe_second);

        swipeLayout1 = (SwipeLayout) view.findViewById(R.id.swipeview_first);
        swipeLayout2 = (SwipeLayout) view.findViewById(R.id.swipeview_second);

        swipeLayout1.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout2.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout1.addDrag(SwipeLayout.DragEdge.Bottom, swipeLayout1.findViewById(R.id.swipe_bottom_wrapper_first));
        swipeLayout2.addDrag(SwipeLayout.DragEdge.Bottom, swipeLayout2.findViewById(R.id.swipe_bottom_wrapper_second));

        swipeLayout1.setRightSwipeEnabled(false);
        swipeLayout1.setLeftSwipeEnabled(false);
        swipeLayout2.setRightSwipeEnabled(false);
        swipeLayout2.setLeftSwipeEnabled(false);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        mChooseDilemmaText1.setTypeface(source_sans_extra_light);
        mChooseDilemmaText2.setTypeface(source_sans_extra_light);
        addContent1.setTypeface(source_sans_extra_light);
        addContent2.setTypeface(source_sans_extra_light);
        mDilemmaTextView.setTypeface(source_sans_bold);


        swipeLayout1.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                swipeLayout2.setBottomSwipeEnabled(false);
                swipeLayout2.setClickable(false);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                swipeLayout2.setBottomSwipeEnabled(true);
                swipeLayout2.setClickable(true);
                swipeLayout2.setAlpha(1.0f);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                setDilemmaAlpha(topOffset, 1);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        swipeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateSwipeJump(swipeLayout1);
            }
        });


        swipeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateSwipeJump(swipeLayout2);
            }
        });



        swipeLayout2.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                swipeLayout1.setBottomSwipeEnabled(false);
                swipeLayout1.setClickable(false);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {
                swipeLayout1.setBottomSwipeEnabled(true);
                swipeLayout1.setClickable(true);
                swipeLayout1.setAlpha(1.0f);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                setDilemmaAlpha(topOffset, 2);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        mChooseDilemmaText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentIndex();
            }
        });

        mChooseDilemmaText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentIndex();
            }
        });

        addContent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContentIntent(1);
            }
        });

        addContent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContentIntent(2);
            }
        });


        // set up the listeners
        mBackgroundInfoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Achtergrond informatie");
                builder.setIcon(R.mipmap.ic_info);
                builder.setMessage(mDilemma.getBackgroundInfo());
                builder.show();
            }
        });

        mSkipDilemma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentIndex();
            }
        });

        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterSwitch.isChecked()) {
                    mFilterFriends = true;

                    if (!mDilemma.isFromAFriend()) {
                        updateCurrentIndex();
                    }
                } else {
                    mFilterFriends = false;
                    resetCurrentIndex();
                }
            }
        });

        return view;
    }

    private void getData() {
        ApiDataFetcher apiDataFetcher = new ApiDataFetcher();
        apiDataFetcher.getData();
    }

    private void updateCurrentIndex() {
        if (mCurrentIndex == mDilemmaList.size() - 1) {
            showNoDilemmas();
            return;
        } else {
            mCurrentIndex++;
        }
        mDilemma = mDilemmaList.get(mCurrentIndex);

        if (!mFilterFriends) {
            updateUi();
        } else {
            if (mDilemma.isFromAFriend()) {
                updateUi();
            } else {
                updateCurrentIndex();
            }
        }

    }

    private void resetCurrentIndex() {
        mNoDilemmasTextView.setVisibility(View.GONE);
        mCurrentIndex = 0;
        updateUi();
    }

    private void showNoDilemmas() {
        mNoDilemmasTextView.setVisibility(View.VISIBLE);
    }

    private void updateUi() {

        if (mCurrentIndex == 4 && !User.getInstance().getHasCreatedDilemma()) {
            askToCreateDilemma();
        }

        mDilemma = mDilemmaList.get(mCurrentIndex);

        swipeLayout1.setAlpha(1.0f);
        swipeLayout2.setAlpha(1.0f);

        swipeLayout1.setClickable(true);
        swipeLayout2.setClickable(true);
        swipeLayout1.setBottomSwipeEnabled(true);
        swipeLayout2.setBottomSwipeEnabled(true);

        // do not show the current user's own mDilemma's
        if (mDilemma.getCreator_fb_id().equals(mUserFbId)) {
            updateCurrentIndex();
            return;
        }

        // check to see if the dilemma is NOT FOR ALL and then check to see if its from a friend
        if (!mDilemma.getIsToAll()) {
            if (!mDilemma.isFromAFriend()) {
                updateCurrentIndex();
                return;
            }
        }

        if (mDilemma.getTitle() != null) {
            mDilemmaTextView.setText(mDilemma.getTitle());
        }

        // make background info icon invisible if background info is not present
        if (mDilemma.getBackgroundInfo().equals("")){
            mBackgroundInfoImageView.setVisibility(View.GONE);
        } else {
            mBackgroundInfoImageView.setVisibility(View.VISIBLE);
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

        if (mDilemma.getPhotoA() != null && mDilemma.getTitlePhotoA() != null) {
            Glide.with(getActivity())
                    .load(mDilemma.getPhotoA())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(mFirstDilemmaImage);
            mFirstDilemmaImageText.setText(mDilemma.getTitlePhotoA());
            mChooseDilemmaText1.setText(getString(R.string.choose_dilemma, mDilemma.getTitlePhotoA().toUpperCase()));
        } else {
            mFirstDilemmaImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_sand_timer));
        }

        if (mDilemma.getPhotoB() != null && mDilemma.getTitlePhotoB() != null) {
            Glide.with(getActivity())
                    .load(mDilemma.getPhotoB())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(mSecondDilemmaImage);
            mSecondDilemmaImageText.setText(mDilemma.getTitlePhotoB());
            mChooseDilemmaText2.setText(getString(R.string.choose_dilemma, mDilemma.getTitlePhotoB().toUpperCase()));
        } else {
            mSecondDilemmaImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_sand_timer));
        }


        // set creator and mDilemma text
        if (!mDilemma.getIsAnonymous()) {
            mUserNameTextView.setText(mDilemma.getCreator_name());
            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + mDilemma.getCreator_age() + " jaar | " + mDilemma.getCreator_hometown());
        } else {
            mUserNameTextView.setText(getString(R.string.dilemma_username));

            String ageToShow = "Leeftijd onbekend";
            if (!mDilemma.getCreator_age().equals("Leeftijd onbekend")) {
                ageToShow = mDilemma.getCreator_ageRange();
            }
            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + ageToShow + " jaar | " + mDilemma.getCreator_hometown());
        }

        if (mDilemma.isFromAFriend()) {
            mFriendIcon.setVisibility(View.VISIBLE);
        } else {
            mFriendIcon.setVisibility(View.GONE);
        }

    }


    private void createDummyDate() {
        // SET UP TEST DATA!
        Dilemma dilemma_1 = new Dilemma();
        dilemma_1.setTitle("Ik heb bloemen gekregen. Moet ik ze in een mooie vaas stoppen of in een houten kist?");
        dilemma_1.setBackgroundInfo("");
        dilemma_1.setUuid();
        dilemma_1.setCreator_fb_id("10157925351030158");
        dilemma_1.setCreator_name("Jarle Matland");
        dilemma_1.setCreator_age("32");
        dilemma_1.setCreator_sex("Man");
        dilemma_1.setCreator_picture_url("https://scontent.xx.fbcdn.net/v/t1.0-1/c99.0.706.706/s320x320/602095_10153281849525158_1999443146_n.jpg?oh=3f4114034115e081fea631c0b4d30335&oe=58E2B1E7");
        dilemma_1.setPhotoA("http://s.hswstatic.com/gif/cremation-urn.jpg");
        dilemma_1.setPhotoB("http://www.gayworld.be/wp-content/uploads/2009/10/uitvaart-begrafenis-stephen-gately-300x252.jpg");
        dilemma_1.setTitlePhotoA("Vase");
        dilemma_1.setCreator_hometown("Amsterdam");
        dilemma_1.setTitlePhotoB("Box");
        dilemma_1.setDeadline(12);
        dilemma_1.setCreatedAt();
        dilemma_1.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies1 = new Replies();
        List<String> mFacebookIDs = new ArrayList<>();
        mFacebookIDs.add("1272797916114496");
        mFacebookIDs.add("103858760112750");
        mFacebookIDs.add("10157925351030158");
        Answer optionA = new Answer("4", "5", "2", "5", "2", "2", "6", mFacebookIDs);
        Answer optionB = new Answer("6", "10", "5", "10", "1", "5", "6", mFacebookIDs);
        replies1.setOptionAAnswers(optionA);
        replies1.setOptionBAnswers(optionB);
        dilemma_1.setReplies(replies1);
        Contents contents1 = new Contents();
        Content contentA = new Content("Hello, this is a test", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        Content contentB = new Content("Hello, this is also a test, but for Content B", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        contents1.addContentToOptionA(contentA);
        contents1.addContentToOptionB(contentB);
        dilemma_1.setContents(contents1);

        Dilemma dilemma_2 = new Dilemma();
        dilemma_2.setTitle("Moet ik als man alleen toch ook een kerstboom optuigen?");
        dilemma_2.setBackgroundInfo("");
        dilemma_2.setUuid();
        dilemma_2.setCreator_fb_id("103858760112750");
        dilemma_2.setCreator_name("Michael McDonaldberg");
        dilemma_2.setCreator_age("45");
        dilemma_2.setCreator_sex("Man");
        dilemma_2.setCreator_hometown("Amsterdam");
        dilemma_2.setCreator_picture_url("https://scontent.xx.fbcdn.net/v/t1.0-1/c99.0.706.706/s320x320/602095_10153281849525158_1999443146_n.jpg?oh=3f4114034115e081fea631c0b4d30335&oe=58E2B1E7");
        dilemma_2.setPhotoA("http://cvandaag.nl/wp-content/uploads/2015/12/Geen-Kerst-2.jpg");
        dilemma_2.setPhotoB("https://www.schoolplaten.com/img/crafts-att/16310-att%20kerstboom%201.jpg");
        dilemma_2.setTitlePhotoA("Geen boom");
        dilemma_2.setTitlePhotoB("Wel een boom");
        dilemma_2.setDeadline(12);
        dilemma_2.setCreatedAt();
        dilemma_2.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies2 = new Replies();
        List<String> mFacebookIDs2 = new ArrayList<>();
        mFacebookIDs2.add("10157925351030158");
        mFacebookIDs2.add("103858760112750");
        mFacebookIDs2.add("10210277093237768");
        Answer optionA2 = new Answer("4", "5", "2", "5", "2", "2", "6", mFacebookIDs2);
        Answer optionB2 = new Answer("6", "10", "5", "10", "1", "5", "6", mFacebookIDs2);
        replies2.setOptionAAnswers(optionA2);
        replies2.setOptionBAnswers(optionB2);
        dilemma_2.setReplies(replies2);
        Contents contents2 = new Contents();
        Content contentA2 = new Content("Hello, this is a test", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        Content contentB2 = new Content("Hello, this is also a test, but for Content B", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        contents2.addContentToOptionA(contentA2);
        contents2.addContentToOptionB(contentB2);
        dilemma_2.setContents(contents2);

        Dilemma dilemma_3 = new Dilemma();
        dilemma_3.setTitle("Ik heb 100 euro. Welk schilderij zal ik kopen?");
        dilemma_3.setBackgroundInfo("");
        dilemma_3.setUuid();
        dilemma_3.setCreator_fb_id("10210277093237768");
        dilemma_3.setCreator_name("Eliza Cambre");
        dilemma_3.setCreator_age("25");
        dilemma_3.setCreator_sex("Vrouw");
        dilemma_3.setCreator_hometown("Amsterdam");
        dilemma_3.setCreator_picture_url("https://scontent.xx.fbcdn.net/v/t1.0-1/c90.41.576.576/s320x320/15337588_10209957675732530_6982606016405059815_n.jpg?oh=af0857e17e686184f7a2355abc5e6b5e&oe=591EC9B3");
        dilemma_3.setPhotoA("https://nyoobserver.files.wordpress.com/2013/12/vermeer-670-girl-with-a-pearl-earring_2000.jpg");
        dilemma_3.setPhotoB("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa,_by_Leonardo_da_Vinci,_from_C2RMF_retouched.jpg/266px-Mona_Lisa,_by_Leonardo_da_Vinci,_from_C2RMF_retouched.jpg");
        dilemma_3.setTitlePhotoA("Meisje met een oorbel");
        dilemma_3.setTitlePhotoB("Meisje met een lach");
        dilemma_3.setDeadline(12);
        dilemma_3.setCreatedAt();
        dilemma_3.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies3 = new Replies();
        List<String> mFacebookIDs3 = new ArrayList<>();
        mFacebookIDs3.add("1272797916114496");
        mFacebookIDs3.add("10157925351030158");
        mFacebookIDs3.add("10210277093237768");
        Answer optionA3 = new Answer("4", "5", "2", "5", "2", "2", "6", mFacebookIDs3);
        Answer optionB3 = new Answer("6", "10", "5", "10", "1", "5", "6", mFacebookIDs3);
        replies3.setOptionAAnswers(optionA3);
        replies3.setOptionBAnswers(optionB3);
        dilemma_3.setReplies(replies3);
        Contents contents3 = new Contents();
        Content contentA3 = new Content("Hello, this is a test", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        Content contentB3 = new Content("Hello, this is also a test, but for Content B", true, "Paul van Cappelle", "1272797916114496", "30", "Man", "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/14054015_1154486661278956_2640202812254557417_n.jpg?oh=6b2f0af784e478e8debf70221af2a05c&oe=591F024B", "Amsterdam");
        contents3.addContentToOptionA(contentA3);
        contents3.addContentToOptionB(contentB3);
        dilemma_3.setContents(contents3);

        mDilemmaList.add(dilemma_1);
        mDilemmaList.add(dilemma_2);
        mDilemmaList.add(dilemma_3);
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
            default:
                return true;
        }
    }

    public static void addDilemmaToTempList(Dilemma dilemma) {
        mTempDilemmaList.add(dilemma);
    }

    private void addTempDilemmas() {
        if (mTempDilemmaList != null) {
            int i = 0;

            while (i < mTempDilemmaList.size()) {
                mDilemmaList.add(mTempDilemmaList.get(i));
                i += 1;
            }
        }
    }

    private void addContentIntent(int answer) {
        Dilemma dilemma = mDilemmaList.get(mCurrentIndex);

        String uuid = dilemma.getUuid();
        String optionChosen = "";

        if (answer == 1) {
            optionChosen = "optionA";
        } else if (answer == 2) {
            optionChosen = "optionB";
        }

        Intent i = new Intent(AddContentActivity.newIntent(getContext()));
        i.putExtra(DILEMMA_OBJECT, dilemma);
        i.putExtra(DILEMMA_ANSWER_ADD_CONTENT, optionChosen);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSavedInstanceSTate");
        outState.putInt(CURRENT_INDEX, mCurrentIndex);
        outState.putString(USER_ID, mUserFbId);
    }


    /**
     * inner class
     * <p>
     * <p>
     * Created by paulvancappelle on 16-12-16.
     */
    public class ApiDataFetcher {

        private static final String BASE_URL = "http://www.mocky.io/";
        private static final String TAG = "kiespijn.ApiDataFetcher";


        public ApiDataFetcher() {
            // empty constructor
        }

        public void getData() {

            // Logging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Gson gson = new GsonBuilder().setLenient().create();
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            ApiEndpointInterface apiResponse = retrofit.create(ApiEndpointInterface.class);

            apiResponse.getDilemmaList().enqueue(new Callback<DilemmaApiResponse>() {
                @Override
                public void onResponse(Call<DilemmaApiResponse> call, Response<DilemmaApiResponse> response) {
                    Log.e(TAG, "Retrofit response");
                    setResponse(response);
                }

                @Override
                public void onFailure(Call<DilemmaApiResponse> call, Throwable t) {
                    Log.e(TAG, "Retrofit error: " + t.getMessage());
                }
            });

        }

        private void setResponse(Response<DilemmaApiResponse> response) {
            DilemmaApiResponse mDilemmaApiResponse = response.body();
            if (response.body() == null) {
                Log.e(TAG, "Retrofit body null: " + String.valueOf(response.code()));
            }
            mDilemmaList = mDilemmaApiResponse.getDilemmaList();
            Log.v("mDilemmaList", String.valueOf(response.body().getDilemmaList().size()));
            createDummyDate();
            addTempDilemmas();
            updateUi();
        }

    }

    private void setDilemmaAlpha(int offset, int dilemma) {
        if (offset > tempOffset) {
            if (mImageAlpha >= 0.1f && mImageAlpha <= 1.0f) {
                mImageAlpha = mImageAlpha + 0.04f;
            }
        } else if (offset < tempOffset) {
            if (mImageAlpha >= 0.1f && mImageAlpha <= 1.0f) {
                mImageAlpha = mImageAlpha - 0.04f;
            }
        }

        if (mImageAlpha < 0.1f) {
            mImageAlpha = 0.1f;
        } else if (mImageAlpha > 1.0f) {
            mImageAlpha = 1.0f;
        }

        if (dilemma == 1) {
            swipeLayout2.setAlpha(mImageAlpha);
        } else if (dilemma == 2) {
            swipeLayout1.setAlpha(mImageAlpha);
        }

        tempOffset = offset;
    }

    private void animateSwipeJump(SwipeLayout swipelayout) {

        ObjectAnimator animUp = ObjectAnimator.ofFloat(swipelayout, "translationY", swipelayout.getTop(), swipelayout.getTop() - 50);
        ObjectAnimator animDown = ObjectAnimator.ofFloat(swipelayout, "translationY", swipelayout.getTop() - 50, swipelayout.getTop());
        animDown.setInterpolator(new BounceInterpolator());

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animUp).before(animDown);
        animSet.setDuration(300);
        animSet.start();
    }

    private void askToCreateDilemma() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.create_dilemma_yourself))
                .setCancelable(false)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Dilemma dilemma = new Dilemma();
                        Intent intent = CreateDilemmaActivity.newIntent(getActivity());
                        intent.putExtra(DILEMMA_OBJECT, dilemma);
                        startActivity(intent);
                        User.getInstance().setHasCreatedDilemma(true);
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}