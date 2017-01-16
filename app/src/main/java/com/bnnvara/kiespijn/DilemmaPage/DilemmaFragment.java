package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Context;
import android.content.Intent;
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
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
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

    // Views
    private ImageView mUserPhotoImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private LinearLayout mNoDilemmasTextView;
    private ImageView mBackgroundInfoImageView;
    private Button mDilemmaFirstAddContent;
    private Button mDilemmaSecondAddContent;
    private Button mSkipDilemma;
    private ImageView mFriendIcon;

    private ImageView mFirstDilemmaImage;
    private ImageView mSecondDilemmaImage;
    private TextView mFirstDilemmaImageText;
    private TextView mSecondDilemmaImageText;

    private static List<Dilemma> mDilemmaList;
    private static List<Dilemma> mTempDilemmaList = new ArrayList<>();
    private Dilemma mDilemma;
    private int mCurrentIndex;
    private String mUserFbId;
    private Boolean mFilterFriends = false;

    //    private SwipePlaceHolderView mSwipeView1;
//    private SwipePlaceHolderView mSwipeView2;
    private Context mContext;

    private SwipeLayout swipeLayout1;
    private SwipeLayout swipeLayout2;

    public static Fragment newInstance() {
        return new DilemmaFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dilemma_page, container, false);

        mUserFbId = User.getInstance().getUserKey();
//        mSwipeView1 = (SwipePlaceHolderView) view.findViewById(R.id.swipeView);
//        mSwipeView2 = (SwipePlaceHolderView) view.findViewById(R.id.swipeView2);
        mContext = getContext();

        //Toast.makeText(getActivity(), mUserFbId, Toast.LENGTH_LONG).show();
        getData();

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma);
        mNoDilemmasTextView = (LinearLayout) view.findViewById(R.id.linear_layout_no_dilemmas);
        mBackgroundInfoImageView = (ImageView) view.findViewById(R.id.image_view_background_info);
        final SwitchCompat filterSwitch = (SwitchCompat) view.findViewById(R.id.dilemma_filter_switch);
        mDilemmaFirstAddContent = (Button) view.findViewById(R.id.button_add_content_first);
        mDilemmaSecondAddContent = (Button) view.findViewById(R.id.button_add_content_second);
        mSkipDilemma = (Button) view.findViewById(R.id.button_skip_dilemma);
        mFriendIcon = (ImageView) view.findViewById(R.id.imageview_friends);

        filterSwitch.setChecked(false);

        mFirstDilemmaImage = (ImageView) view.findViewById(R.id.image_view_first_option_decicision_page);
        mSecondDilemmaImage = (ImageView) view.findViewById(R.id.image_view_second_option_decicision_page);
        mFirstDilemmaImageText = (TextView) view.findViewById(R.id.text_view_first_image_title);
        mSecondDilemmaImageText = (TextView) view.findViewById(R.id.text_view_second_image_title);

        final TextView nextDilemma1 = (TextView) view.findViewById(R.id.textview_next_dilemma_1);
        final TextView nextDilemma2 = (TextView) view.findViewById(R.id.textview_next_dilemma);
        final TextView addContent = (TextView) view.findViewById(R.id.textview_add_content_swipe);

        swipeLayout1 = (SwipeLayout) view.findViewById(R.id.swipeview_first);
        swipeLayout2 = (SwipeLayout) view.findViewById(R.id.swipeview_second);

        swipeLayout1.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout2.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout1.addDrag(SwipeLayout.DragEdge.Left, swipeLayout1.findViewById(R.id.swipe_bottom_wrapper_first));
        swipeLayout1.addDrag(SwipeLayout.DragEdge.Top, swipeLayout1.findViewById(R.id.swipe_bottom_wrapper_second));
        swipeLayout2.addDrag(SwipeLayout.DragEdge.Bottom, swipeLayout2.findViewById(R.id.swipe_bottom_wrapper_second));

        swipeLayout1.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                nextDilemma1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateCurrentIndex();
                    }
                });
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        swipeLayout2.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                nextDilemma2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateCurrentIndex();
                    }
                });

                addContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addContentIntent(2);
                    }
                });
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

//        mSwipeView1.getBuilder()
//                .setDisplayViewCount(3)
//                .setSwipeDecor(new SwipeDecor()
//                        .setPaddingTop(5)
//                        .setRelativeScale(0.01f)
//                        .setSwipeInMsgLayoutId(R.layout.swipe_card_chosen_message)
//                        .setSwipeOutMsgLayoutId(R.layout.swipe_card_chosen_message));
//
//        mSwipeView2.getBuilder()
//                .setDisplayViewCount(3)
//                .setSwipeDecor(new SwipeDecor()
//                        .setPaddingTop(5)
//                        .setRelativeScale(0.01f)
//                        .setSwipeInMsgLayoutId(R.layout.swipe_card_chosen_message)
//                        .setSwipeOutMsgLayoutId(R.layout.swipe_card_chosen_message));

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

        mDilemmaFirstAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContentIntent(1);
            }
        });

        mDilemmaSecondAddContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContentIntent(2);
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
        mDilemma = mDilemmaList.get(mCurrentIndex);

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
        }

        if (mDilemma.getPhotoB() != null && mDilemma.getTitlePhotoB() != null) {
            Glide.with(getActivity())
                    .load(mDilemma.getPhotoB())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_sand_timer)
                    .into(mSecondDilemmaImage);
            mSecondDilemmaImageText.setText(mDilemma.getTitlePhotoB());
        }

        // set creator and mDilemma text
        if (!mDilemma.getIsAnonymous()) {
            mUserNameTextView.setText(mDilemma.getCreator_name());
            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + mDilemma.getCreator_age());
        } else {
            mUserNameTextView.setText(getString(R.string.dilemma_username));

            String ageToShow = "Leeftijd onbekend";
            if (!mDilemma.getCreator_age().equals("Leeftijd onbekend")) {
                ageToShow = mDilemma.getCreator_ageRange();
            }
            mUserDescriptionTextView.setText(mDilemma.getCreator_sex() + " | " + ageToShow);
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
}