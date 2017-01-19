package com.bnnvara.kiespijn.GoogleImageSearch;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.bnnvara.kiespijn.Login.LoginFragment;
import com.bnnvara.kiespijn.R;
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

public class GoogleSearchFragment extends Fragment {

    private static final String TAG = "PhotoGalleryFragment";
    private static final String GOOGLE_IMAGE_URL = "google_image_url";

    private RecyclerView mPhotoRecylerView;
    private static String mSearchString;
    private static List<GalleryItem> mGalleryItems;
    private String mChosenURL;
    private List<CheckBox> mRadioButtonList;
    private List<ImageView> mImageViewList;
    private android.widget.SearchView mSearchView;
    private int mImageIndex = 1;
    private long mTotalImageSize;
    private MenuItem mNewSearch;

    public static GoogleSearchFragment newInstance(String searchString) {
        mSearchString = searchString;
        return new GoogleSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mRadioButtonList = new ArrayList<>();
        mImageViewList = new ArrayList<>();

        if (mSearchString != null && isConnected()) {
            getImages();
        }

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
                resultIntent.putExtra(GOOGLE_IMAGE_URL, mChosenURL);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_google_search, container, false);

        mPhotoRecylerView = (RecyclerView) view.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecylerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mPhotoRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(dy)) {

                    if (mImageIndex < (mTotalImageSize - 10)) {
                        mImageIndex += 10;
                        getImages();
                    }
                }
            }
        });

        mSearchView = (android.widget.SearchView) view.findViewById(R.id.google_search_view);

        mSearchView.setVisibility(View.GONE);

        mSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchString = mSearchView.getQuery().toString();

                if (isConnected()) {
                    getImages();
                }


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

    private void getImages() {

        String apiKey = getString(R.string.google_search_api_key);
        String cx = getString(R.string.google_cx_key);

        // Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(getString(R.string.google_baseURL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GoogleApiRestInterface apiResponse = restAdapter.create(GoogleApiRestInterface.class);

        Call<GoogleImageApiResponse> mGalleryResponse = apiResponse.customSearch(apiKey, cx, mSearchString, String.valueOf(mImageIndex));

        mGalleryResponse.enqueue(new Callback<GoogleImageApiResponse>() {
            @Override
            public void onResponse(Call<GoogleImageApiResponse> call, Response<GoogleImageApiResponse> response) {
                GoogleImageApiResponse mGoogleImageApiResponse = response.body();

                if (response.body() == null) {
                    Log.e("Retrofit body null", String.valueOf(response.code()));
                }

                if (mGoogleImageApiResponse != null && response.body() != null) {

                    if (mGoogleImageApiResponse.getGalleryItems() != null) {
                        mGalleryItems = mGoogleImageApiResponse.getGalleryItems();
                        mTotalImageSize = mGoogleImageApiResponse.getTotalImages();
                    }

                    if (mPhotoRecylerView != null && mGalleryItems != null) {
                        mPhotoRecylerView.setAdapter(new PhotoAdapter(mGalleryItems));
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.google_no_photos), Toast.LENGTH_SHORT).show();
                    mSearchView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<GoogleImageApiResponse> call, Throwable t) {
                Log.e("Retrofit error", t.getMessage());
            }
        });

    }

    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mItemImageView;
        private CheckBox mRadioButton;
        private GalleryItem mGalleryItem;

        public PhotoHolder(View itemView) {
            super(itemView);
            mRadioButton = (CheckBox) itemView.findViewById(R.id.radioButton);
            mItemImageView = (ImageView) itemView.findViewById(R.id.iv_photo_gallery_fragment);
            itemView.setOnClickListener(this);
        }

        public void bindGalleryItem(GalleryItem item) {
            mGalleryItem = item;
            String url = mGalleryItem.getUrl();
            mGalleryItem.setImageURL(url);
            mRadioButtonList.add(mRadioButton);
            mImageViewList.add(mItemImageView);
            Log.i(TAG, url);

            Glide.with(getActivity())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mItemImageView);
        }

        @Override
        public void onClick(View view) {
            int i = 0;

            while (i < mRadioButtonList.size()) {
                mRadioButtonList.get(i).setChecked(false);
                mImageViewList.get(i).setAlpha(1f);
                i += 1;
            }

            mRadioButton.setChecked(true);
            mItemImageView.setAlpha(0.7f);
            mChosenURL = mGalleryItem.getImageURL();

        }

    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.google_search_gallery_item, parent, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem mGalleryItem = mGalleryItems.get(position);
            holder.bindGalleryItem(mGalleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }

        public void setupAdapter() {
            if (isAdded()) {
                mPhotoRecylerView.setAdapter(new PhotoAdapter(mGalleryItems));
            }
        }
    }

    public boolean isConnected() {
        //Checks if the device is connected to the internet (WIFI or mobile data)
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else

            return false;
    }
}
