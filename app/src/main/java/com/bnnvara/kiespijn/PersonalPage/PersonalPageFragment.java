package com.bnnvara.kiespijn.PersonalPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.zip.Inflater;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paulvancappelle on 20-12-16.
 */
public class PersonalPageFragment extends Fragment {

    // view variables
    Button mMineButton;
    Button mOhtersButton;
    Button mRunningButton;
    Button mClosedButton;

    // regular variables
    DilemmaAdapter mDilemmaAdapter;
    RecyclerView mRecyclerView;
    List<Dilemma> mDilemmaList;
    List<Dilemma> mMyRunningDilemmaList;
    List<Dilemma> mMyClosedDilemmaList;
    List<Dilemma> mOthersRunningDilemmaList;
    List<Dilemma> mOthersClosedDilemmaList;
    boolean mShowMyDilemmas = true;
    boolean mShowRunning;


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

        ApiDataFetcher apiDataFetcher = new ApiDataFetcher();
        apiDataFetcher.getData();

        // set references
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_personal_page);
        mMineButton = (Button) view.findViewById(R.id.button_pers_page_mine);
        mOhtersButton = (Button) view.findViewById(R.id.button_pers_page_others);
        mRunningButton = (Button) view.findViewById(R.id.button_pers_page_running);
        mClosedButton = (Button) view.findViewById(R.id.button_pers_page_closed);

        // set the RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mDilemmaAdapter);

        // set listeners
        mMineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mOhtersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRunningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mClosedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                return true;
            case R.id.menu_item_login:
                Intent intent2 = LoginActivity.newIntent(getActivity());
                startActivity(intent2);
                return true;
            case R.id.menu_item_create_dilemma:
                Intent intent3 = CreateDilemmaActivity.newIntent(getActivity());
                startActivity(intent3);
                return true;
            default:
                return true;
        }
    }

    private void updateUi() {

        if (mShowMyDilemmas) {
            if (mShowRunning) {

            } else {

            }
        } else if (mShowRunning) {

        } else {

        }

        mDilemmaAdapter = new DilemmaAdapter(mDilemmaList);
        mRecyclerView.setAdapter(mDilemmaAdapter);
    }


    private void createDilemmaLists() {

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
            holder.mUserNameTextView.setText(mDilemmaListToShow.get(position).getCreator_name());
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
    public class DilemmaHolder extends RecyclerView.ViewHolder {

        private ImageView mUserPhotoImageView;
        private TextView mUserNameTextView;
        private TextView mUserDescriptionTextView;
        private TextView mDilemmaTextView;

        public DilemmaHolder(View itemView) {
            super(itemView);
            mUserPhotoImageView = (ImageView) itemView.findViewById(R.id.image_view_user_photo_personal_page);
            mUserNameTextView = (TextView) itemView.findViewById(R.id.text_view_username_personal_page);
            mUserDescriptionTextView = (TextView) itemView.findViewById(R.id.text_view_user_info_personal_page);
            mDilemmaTextView = (TextView) itemView.findViewById(R.id.text_view_dilemma_personal_page);
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
