package com.bnnvara.kiespijn.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

import com.bnnvara.kiespijn.ApiEndpointInterface;
import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.Login.LoginActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.ResultPage.ResultActivity;
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

public class PersonalPageFragment extends Fragment {

    // constants
    private static final String TAG = PersonalPageFragment.class.getSimpleName();
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String LOGGING_OUT = "logging_out";

    // view variables
    private SwitchCompat mFilterSwitch;

    // regular variables
    private DilemmaAdapter mDilemmaAdapter;
    private RecyclerView mRecyclerView;
    private List<Dilemma> mDilemmaList;
    private List<Dilemma> mMyRunningDilemmaList = new ArrayList<>();
    private List<Dilemma> mMyClosedDilemmaList = new ArrayList<>();
    private List<Dilemma> mOthersRunningDilemmaList = new ArrayList<>();
    private List<Dilemma> mOthersClosedDilemmaList = new ArrayList<>();
    private boolean mShowMyDilemmas = true;
    private boolean mShowRunning;
    private String mUserFbId;


    public static PersonalPageFragment newInstance() {
        return new PersonalPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_page, container, false);

        mUserFbId = User.getInstance().getUserKey();

        ApiDataFetcher apiDataFetcher = new ApiDataFetcher();
        apiDataFetcher.getData();

        // set references
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_personal_page);
        mFilterSwitch = (SwitchCompat) view.findViewById(R.id.switch_compat_own_others);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mDilemmaAdapter);

        // set listeners
        mFilterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFilterSwitch.isChecked()) {
                    mShowMyDilemmas = false; // if checked, means right option so show others' dilemma's
                    updateUi();

                } else {
                    mShowMyDilemmas = true;
                    updateUi();
                }
            }
        });

        return view;
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
                // has to do nothing now, because user is already on the Personal Page
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

    private void updateUi() {
        if (mShowMyDilemmas) {
            ArrayList<Dilemma> myDilemmaList = new ArrayList<>();
            myDilemmaList.addAll(mMyRunningDilemmaList);
            myDilemmaList.addAll(mMyClosedDilemmaList);
            mDilemmaAdapter = new DilemmaAdapter(myDilemmaList);
        } else {
            ArrayList<Dilemma> othersDilemmaList = new ArrayList<>();
            othersDilemmaList.addAll(mOthersRunningDilemmaList);
            othersDilemmaList.addAll(mOthersClosedDilemmaList);
            mDilemmaAdapter = new DilemmaAdapter(othersDilemmaList);
        }

        mRecyclerView.setAdapter(mDilemmaAdapter);
    }


    private void createDilemmaLists() {
        long now = 1484639254;

        for (Dilemma dilemma: mDilemmaList){

            // determine timeLeft of the dilemma
            boolean isRunning = dilemma.getDeadline() > (now);
            int timeLeft = (int) ((dilemma.getDeadline() - (now) ) / 1000);
            dilemma.setTimeLeft(timeLeft);

            // determine if current user answered for the dilemma (and is not the creator)
            boolean isAnsweredByCurrentUser = dilemma.isAnsweredByCurrentUser();

            if (dilemma.getCreator_fb_id().equals(mUserFbId) && isRunning) {
                mMyRunningDilemmaList.add(dilemma);
            } else if (dilemma.getCreator_fb_id().equals(mUserFbId) && !isRunning) {
                mMyClosedDilemmaList.add(dilemma);
            } else if (isAnsweredByCurrentUser && isRunning){
                mOthersRunningDilemmaList.add(dilemma);
            } else if (isAnsweredByCurrentUser && !isRunning) {
                mOthersClosedDilemmaList.add(dilemma);
            } else {
                Log.i("dilemma", "kansloos");
            }
        }
    }


    /**
     * inner class
     * <p>
     * <p>
     * Created by paulvancappelle on 16-12-16.
     */
    public class DilemmaAdapter extends RecyclerView.Adapter<DilemmaHolder> {

        List<Dilemma> mDilemmaListToShow;

        public DilemmaAdapter(List<Dilemma> dilemmaListToShow) {
            mDilemmaListToShow = dilemmaListToShow;
        }

        @Override
        public DilemmaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.dilemma_viewholder, parent, false);
            return new DilemmaHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DilemmaHolder holder, int position) {
            Dilemma dilemma = mDilemmaListToShow.get(position);
            holder.setDilemma(dilemma);
            holder.itemView.setOnClickListener(holder);

            if (dilemma.getCreator_hometown() == null) {
                dilemma.setCreator_hometown("Onbekend");
            }

            if (dilemma.getIsAnonymous()) {
                String ageToShow = "Leeftijd onbekend";
                if (!dilemma.getCreator_age().equals("Leeftijd onbekend")) {
                    ageToShow = dilemma.getCreator_ageRange();
                }

                holder.mUserDescriptionTextView.setText(dilemma.getCreator_sex() + " | " + ageToShow + " jaar | " + dilemma.getCreator_hometown());
                holder.mUserNameTextView.setText(R.string.anonymous);

            } else {
                holder.mUserDescriptionTextView.setText(
                        dilemma.getCreator_sex() + " | " + dilemma.getCreator_age() + " jaar | " + dilemma.getCreator_hometown());
                holder.mUserNameTextView.setText(dilemma.getCreator_name());
            }
            holder.mDilemmaTextView.setText(dilemma.getTitle());

            // set user profile picture
            if (dilemma.getCreator_picture_url() != null && !dilemma.getIsAnonymous()) {
                Glide.with(getActivity())
                        .load(dilemma.getCreator_picture_url())
                        .centerCrop()
                        .placeholder(R.drawable.ic_action_sand_timer)
                        .into(holder.mUserPhotoImageView);
            } else if (dilemma.getIsAnonymous() || dilemma.getCreator_picture_url() == null) {
                holder.mUserPhotoImageView.setImageResource(R.drawable.ic_action_user_photo);
            }

            if (dilemma.getTimeLeft() < 0){
                holder.mTimeLeftTextView.setText("---");
            } else if (dilemma.getTimeLeft() == 1){
                holder.mTimeLeftTextView.setText(" 1 uur ");
            } else {
                holder.mTimeLeftTextView.setText(dilemma.getTimeLeft() + " uren");
            }
        }

        @Override
        public int getItemCount() {
            return mDilemmaListToShow.size();
        }
    }

    /**
     * inner class
     * <p>
     * <p>
     * Created by paulvancappelle on 16-12-16.
     */
    public class DilemmaHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Dilemma mDilemma;

        private ImageView mUserPhotoImageView;
        private ImageView mClockImageView;
        private TextView mUserNameTextView;
        private TextView mUserDescriptionTextView;
        private TextView mDilemmaTextView;
        private TextView mTimeLeftTextView;

        public DilemmaHolder(View itemView) {
            super(itemView);
            mUserPhotoImageView = (ImageView) itemView.findViewById(R.id.image_view_user_photo_personal_page);
            mClockImageView = (ImageView) itemView.findViewById(R.id.image_view_clock);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.text_view_username_personal_page);
            mUserDescriptionTextView = (TextView) itemView.findViewById(R.id.text_view_user_info_personal_page);
            mDilemmaTextView = (TextView) itemView.findViewById(R.id.text_view_dilemma_personal_page);
            mTimeLeftTextView = (TextView) itemView.findViewById(R.id.text_view_time_left);
        }

        public void setDilemma(Dilemma dilemma) {
            mDilemma = dilemma;
        }

        @Override
        public void onClick(View view) {
            Intent i = ResultActivity.newIntent(getActivity(), mDilemma);
            startActivity(i);
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
            createDilemmaLists();
            updateUi();
        }

    }
}
