package com.bnnvara.kiespijn.ArticleSearchPage;


import android.app.Activity;
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
import android.widget.CheckBox;
import android.widget.TextView;

import com.bnnvara.kiespijn.ApiEndpointInterface;
import com.bnnvara.kiespijn.CapiModel.Article;
import com.bnnvara.kiespijn.CapiModel.ArticleRoot;
import com.bnnvara.kiespijn.CapiModel.CapiApiResponse;
import com.bnnvara.kiespijn.R;
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

public class ArticleSearchFragment extends Fragment {

    private static final String TAG = ArticleSearchFragment.class.getSimpleName();
    private static final String ARTICLE_URL = "article_url";

    private RecyclerView mArticleRecylerView;
    private String mSearchString;
    private List<ArticleRoot> mArticleRootList;
    private String mChosenURL;
    private List<CheckBox> mRadioButtonList = new ArrayList<>();
    private android.widget.SearchView mSearchView;
    private MenuItem mNewSearch;

    public static ArticleSearchFragment newInstance() {
        return new ArticleSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_search, container, false);

        mArticleRecylerView = (RecyclerView) view.findViewById(R.id.fragment_article_recycler_view);
        mArticleRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Retrieve Article Data CAPI
        ApiDataFetcher apiDataFetcher = new ApiDataFetcher();
        apiDataFetcher.getData();

        mSearchView = (android.widget.SearchView) view.findViewById(R.id.article_search_view);
        mSearchView.setVisibility(View.GONE);
        mSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchString = mSearchView.getQuery().toString();
//                getImages();
                mSearchView.setVisibility(View.GONE);
                mNewSearch.setVisible(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_google_search, menu);

        MenuItem useImage = menu.findItem(R.id.menu_google_search_use);
        mNewSearch = menu.findItem(R.id.menu_google_search);

        useImage.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra(ARTICLE_URL, mChosenURL);
                getActivity().setResult(Activity.RESULT_OK, resultIntent);
                getActivity().finish();

                return false;
            }
        });

        mNewSearch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                mSearchView.setVisibility(View.VISIBLE);
                mNewSearch.setVisible(false);
                return false;
            }
        });

    }

    //
    // INNER CLASS
    //
    //
    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private CheckBox mRadioButton;
        private Article mArticle;

        public PhotoHolder(View itemView) {
            super(itemView);
            mRadioButton = (CheckBox) itemView.findViewById(R.id.radioButton);
            mTitle = (TextView) itemView.findViewById(R.id.itext_view_article_kassa_title);
            itemView.setOnClickListener(this);
        }

        public void bindGalleryItem(Article article) {
            mArticle = article;
            mTitle.setText(article.getTitle());
//            String url = mGalleryItem.getUrl();
//            mGalleryItem.setImageURL(url);
//            mRadioButtonList.add(mRadioButton);
//            mImageViewList.add(mItemImageView);
//            Log.i(TAG, url);
        }

        @Override
        public void onClick(View view) {
//            int i = 0;
//
//            while (i < mRadioButtonList.size()) {
//                mRadioButtonList.get(i).setChecked(false);
//                mImageViewList.get(i).setAlpha(1f);
//                i += 1;
//            }
//
//            mRadioButton.setChecked(true);
//            mItemImageView.setAlpha(0.7f);
//            mChosenURL = mGalleryItem.getImageURL();

        }

    }

    //
    // INNER CLASS
    //
    //
    private class ArticleAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<ArticleRoot> mArticleRoots;

        public ArticleAdapter(List<ArticleRoot> articleList) {
            mArticleRoots = articleList;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.google_search_gallery_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            Article article = mArticleRoots.get(position).getArticle();
            holder.bindGalleryItem(article);
        }

        @Override
        public int getItemCount() {
            return mArticleRoots.size();
        }

//        public void setupAdapter() {
//            if (isAdded()) {
//                mArticleRecylerView.setAdapter(new ArticleAdapter(mArticleList));
//            }
//        }
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

            apiResponse.getCapiResponse().enqueue(new Callback<CapiApiResponse>() {
                @Override
                public void onResponse(Call<CapiApiResponse> call, Response<CapiApiResponse> response) {
                    Log.e(TAG, "Retrofit response");
                    setResponse(response);
                }

                @Override
                public void onFailure(Call<CapiApiResponse> call, Throwable t) {
                    Log.e(TAG, "Retrofit error: " + t.getMessage());
                }
            });

        }

        private void setResponse(Response<CapiApiResponse> response) {
            CapiApiResponse mCapiApiResponse = response.body();
            if (response.body() == null) {
                Log.e(TAG, "Retrofit body null: " + String.valueOf(response.code()));
            }
            mArticleRootList = mCapiApiResponse.getArticleList();
            Log.v("mArticleRootList", String.valueOf(mArticleRootList.size()));
            updateUi();
        }

    }

    private void updateUi() {
        mArticleRecylerView.setAdapter(new ArticleAdapter(mArticleRootList));
    }
}
