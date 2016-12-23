package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bnnvara.kiespijn.ApiEndpointInterface;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *
 */
public class DilemmaFragment extends Fragment {

    // constants
    private static final String TAG = DilemmaFragment.class.getSimpleName();
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String LOGGING_OUT = "logging_out";

    // Views
    private ImageView mUserPhotoImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private TextView mFirstImageTitleTextView;
    private TextView mSecondImageTitleTextView;
    private ImageView mDilemmaFirstImageView;
    private ImageView mDilemmaSecondImageView;

    // regular variables
    private Dilemma mDilemma;
    private static List<Dilemma> mDilemmaList;
    private static List<Dilemma> mTempDilemmaList = new ArrayList<>();
    private int mCurrentIndex;
    private String mUserFbId; // = "101283870370822";


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
        Toast.makeText(getActivity(), mUserFbId, Toast.LENGTH_LONG).show();
        getData();

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma);
        mFirstImageTitleTextView = (TextView) view.findViewById(R.id.text_view_first_image_title);
        mSecondImageTitleTextView = (TextView) view.findViewById(R.id.text_view_second_image_title);
        mDilemmaFirstImageView = (ImageView) view.findViewById(R.id.image_view_first_option_decicision_page);
        mDilemmaSecondImageView = (ImageView) view.findViewById(R.id.image_view_second_option_decicision_page);
        final SwitchCompat filterSwitch = (SwitchCompat) view.findViewById(R.id.dilemma_filter_switch);

        filterSwitch.setChecked(true);

        // set up the listeners
        mDilemmaFirstImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentIndex();
            }
        });
        mDilemmaSecondImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCurrentIndex();
            }
        });

        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterSwitch.isChecked()) {
                    // filter dilemmas to only friends
                } else {
                    // view all dilemmas
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
            mCurrentIndex = 0;
        } else {
            mCurrentIndex++;
        }
        updateUi();
    }

    private void updateUi() {
        mDilemma = mDilemmaList.get(mCurrentIndex);

        // do not show the current user's dilemma's
        if (mDilemma.getCreator_fb_id().equals(mUserFbId)) {
            updateCurrentIndex();
            return;
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

        // load image 1
        mDilemmaTextView.setText(mDilemma.getTitle());
        Glide.with(getActivity())
                .load(mDilemma.getPhotoA())
                .centerCrop()
                .placeholder(R.drawable.ic_action_sand_timer)
                .into(mDilemmaFirstImageView);

        // load image 2
        mDilemmaTextView.setText(mDilemma.getTitle());
        Glide.with(getActivity())
                .load(mDilemma.getPhotoB())
                .centerCrop()
                .placeholder(R.drawable.ic_action_sand_timer)
                .into(mDilemmaSecondImageView);

        // set creator and dilemma text
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

        // set image titles
        mFirstImageTitleTextView.setText(mDilemma.getTitlePhotoA());
        mSecondImageTitleTextView.setText(mDilemma.getTitlePhotoB());

    }


    private void createDummyDate() {
        // SET UP TEST DATA!
        Dilemma dilemma_1 = new Dilemma();
        dilemma_1.setTitle("Ik heb bloemen gekregen. Moet ik ze in een mooie vaas stoppen of in een houten kist?");
        dilemma_1.setUuid();
        dilemma_1.setCreator_fb_id("10156521655410158");
        dilemma_1.setCreator_name("Jarle Matland");
        dilemma_1.setCreator_age("32");
        dilemma_1.setCreator_sex("Man");
        dilemma_1.setPhotoA("http://s.hswstatic.com/gif/cremation-urn.jpg");
        dilemma_1.setPhotoB("http://www.gayworld.be/wp-content/uploads/2009/10/uitvaart-begrafenis-stephen-gately-300x252.jpg");
        dilemma_1.setTitlePhotoA("Vase");
        dilemma_1.setTitlePhotoB("Box");
        dilemma_1.setDeadline(12);
        dilemma_1.setCreatedAt();
        dilemma_1.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies1 = new Replies();
        List<Answer> option1AnswerList = new ArrayList<>();
        List<Answer> option2AnswerList = new ArrayList<>();
        option1AnswerList.add(new Answer("awef-2398"));
        option1AnswerList.add(new Answer("erg-rth98"));
        option2AnswerList.add(new Answer("qweasd-999"));
        option2AnswerList.add(new Answer("lkmlk-8930"));
        replies1.setOption1AnswerList((ArrayList<Answer>) option1AnswerList);
        replies1.setOption2AnswerList((ArrayList<Answer>) option2AnswerList);
        dilemma_1.setReplies(replies1);

        Dilemma dilemma_2 = new Dilemma();
        dilemma_2.setTitle("Moet ik als man alleen toch ook een kerstboom optuigen?");
        dilemma_2.setUuid();
        dilemma_2.setCreator_fb_id("103858760112750");
        dilemma_2.setCreator_name("Michael McDonaldberg");
        dilemma_2.setCreator_age("45");
        dilemma_2.setCreator_sex("Man");
        dilemma_2.setPhotoA("http://cvandaag.nl/wp-content/uploads/2015/12/Geen-Kerst-2.jpg");
        dilemma_2.setPhotoB("https://www.schoolplaten.com/img/crafts-att/16310-att%20kerstboom%201.jpg");
        dilemma_2.setTitlePhotoA("Geen boom");
        dilemma_2.setTitlePhotoB("Wel een boom");
        dilemma_2.setDeadline(12);
        dilemma_2.setCreatedAt();
        dilemma_2.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies2 = new Replies();
        option1AnswerList = new ArrayList<>();
        option2AnswerList = new ArrayList<>();
        option1AnswerList.add(new Answer("awef-2398"));
        option1AnswerList.add(new Answer("erg-rth98"));
        option2AnswerList.add(new Answer("qweasd-999"));
        option2AnswerList.add(new Answer("lkmlk-8930"));
        replies2.setOption1AnswerList((ArrayList<Answer>) option1AnswerList);
        replies2.setOption2AnswerList((ArrayList<Answer>) option2AnswerList);
        dilemma_2.setReplies(replies2);

        Dilemma dilemma_3 = new Dilemma();
        dilemma_3.setTitle("Ik heb 100 euro. Welk schilderij zal ik kopen?");
        dilemma_3.setUuid();
        dilemma_3.setCreator_fb_id("100871300412741");
        dilemma_3.setCreator_name("Marietje Vergeetmenietje");
        dilemma_3.setCreator_age("21");
        dilemma_3.setCreator_sex("Vrouw");
        dilemma_3.setPhotoA("https://nyoobserver.files.wordpress.com/2013/12/vermeer-670-girl-with-a-pearl-earring_2000.jpg");
        dilemma_3.setPhotoB("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Mona_Lisa,_by_Leonardo_da_Vinci,_from_C2RMF_retouched.jpg/266px-Mona_Lisa,_by_Leonardo_da_Vinci,_from_C2RMF_retouched.jpg");
        dilemma_3.setTitlePhotoA("Meisje met een oorbel");
        dilemma_3.setTitlePhotoB("Meisje met een lach");
        dilemma_3.setDeadline(12);
        dilemma_3.setCreatedAt();
        dilemma_3.setIsAnonymous("false");
        dilemma_1.setIsToAll("true");
        Replies replies3 = new Replies();
        option1AnswerList = new ArrayList<>();
        option2AnswerList = new ArrayList<>();
        option1AnswerList.add(new Answer("awef-2398"));
        option1AnswerList.add(new Answer("erg-rth98"));
        option2AnswerList.add(new Answer("qweasd-999"));
        option2AnswerList.add(new Answer("lkmlk-8930"));
        replies3.setOption1AnswerList((ArrayList<Answer>) option1AnswerList);
        replies3.setOption2AnswerList((ArrayList<Answer>) option2AnswerList);
        dilemma_3.setReplies(replies3);

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

    public void addTempDilemmas() {
        if (mTempDilemmaList != null) {
            int i = 0;

            while (i < mTempDilemmaList.size()) {
                mDilemmaList.add(mTempDilemmaList.get(i));
                i += 1;
            }
        }
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