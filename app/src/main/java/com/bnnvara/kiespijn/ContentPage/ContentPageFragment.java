package com.bnnvara.kiespijn.ContentPage;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.ResultPage.ResultActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Text;

import java.util.List;

public class ContentPageFragment extends Fragment {

    private static Dilemma mDilemma;
    private static String mAnswerOption;
    private RecyclerView mPhotoRecylerView;
    private List<Content> mContentList;

    public static Fragment newInstance(Dilemma dilemma, String answerOption) {
        mDilemma = dilemma;
        mAnswerOption = answerOption;
        return new ContentPageFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_content_list, container, false);

        TextView optionText = (TextView) view.findViewById(R.id.content_option_text);
        mPhotoRecylerView = (RecyclerView) view.findViewById(R.id.fragment_content_view_recycler_view);
        mPhotoRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        optionText.setTypeface(source_sans_bold);

        if (mAnswerOption.equals("A")) {
            mContentList = mDilemma.getContents().getOptionAContent();
            optionText.setText(mDilemma.getTitlePhotoA().toUpperCase());
        } else if (mAnswerOption.equals("B")) {
            mContentList = mDilemma.getContents().getOptionBContent();
            optionText.setText(mDilemma.getTitlePhotoB().toUpperCase());
        }

        mPhotoRecylerView.setAdapter(new PhotoAdapter(mContentList));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mUserPhoto;
        private TextView mUserName;
        private TextView mUserInfo;
        private TextView mText;
        private ImageView mImage;
        private TextView mLink;
        private Content mContent;

        public PhotoHolder(View itemView) {
            super(itemView);

            mUserPhoto = (ImageView) itemView.findViewById(R.id.imageview_content_profile_picture);
            mUserName = (TextView) itemView.findViewById(R.id.textview_content_name);
            mUserInfo = (TextView) itemView.findViewById(R.id.textview_content_info);
            mText = (TextView) itemView.findViewById(R.id.textview_content_text);
            mImage = (ImageView) itemView.findViewById(R.id.imageview_content_image);
            mLink = (TextView) itemView.findViewById(R.id.textview_content_link);

            // FONT setup
            Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
            Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
            mUserName.setTypeface(source_sans_bold);
            mUserInfo.setTypeface(source_sans_bold);
            mText.setTypeface(source_sans_extra_light);
            mLink.setTypeface(source_sans_bold);

            mLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(mLink.getText().toString()));
                    startActivity(i);
                }
            });

            itemView.setOnClickListener(this);
        }

        public void bindContentItem(Content content) {
            mContent = content;

            mImage.setVisibility(View.GONE);
            mLink.setVisibility(View.GONE);

            Glide.with(getActivity())
                    .load(mContent.getUserFbPhoto())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mUserPhoto);

            mUserName.setText(mContent.getUserFbName());
            mUserInfo.setText(mContent.getUserFbAge() + ", " + mContent.getUserFbGender() + ", " + mContent.getUserFbHometown());
            mText.setText(mContent.getText());

            if (mContent.getAPhoto()) {

                mImage.setVisibility(View.VISIBLE);

                Glide.with(getActivity())
                        .load(mContent.getPhotoLink())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(mImage);
            } else if (mContent.getAnArticle()) {
                mLink.setVisibility(View.VISIBLE);
                mLink.setText(mContent.getArticleUrl());
            }

        }

        @Override
        public void onClick(View view) {

        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<ContentPageFragment.PhotoHolder> {

        private List<Content> mContentList;

        public PhotoAdapter(List<Content> contentList) {
            mContentList = contentList;
        }

        @Override
        public ContentPageFragment.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View itemView = inflater.inflate(R.layout.content_card, parent, false);
            return new PhotoHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ContentPageFragment.PhotoHolder holder, int position) {
            Content mContent = mContentList.get(position);
            holder.bindContentItem(mContent);
        }

        @Override
        public int getItemCount() {
            return mContentList.size();
        }

        public void setupAdapter() {
            if (isAdded()) {
                mPhotoRecylerView.setAdapter(new PhotoAdapter(mContentList));
            }
        }
    }
}
