package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;
import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.dilemma_swipe_card)
public class DilemmaSwipeCard {

    @View(R.id.text_view_first_image_title)
    private TextView mFirstImageTitle;

    @View(R.id.image_view_first_option_decicision_page)
    private ImageView mFirstImageView;

    private String mImageTitle;
    private Dilemma mDilemma;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;
    private int mDilemmaQuestion;
    private String imageURL;

    public DilemmaSwipeCard(Context context, Dilemma dilemma, SwipePlaceHolderView swipeView, int question) {
        mContext = context;
        mDilemma = dilemma;
        mSwipeView = swipeView;
        mDilemmaQuestion = question;
    }

    @Resolve
    private void onResolved() {

        if (mDilemmaQuestion == 1) {
            imageURL = mDilemma.getPhotoA();
            mImageTitle = mDilemma.getTitlePhotoA();
        } else if (mDilemmaQuestion == 2) {
            imageURL = mDilemma.getPhotoB();
            mImageTitle = mDilemma.getTitlePhotoB();
        }

        Glide.with(mContext).load(imageURL).into(mFirstImageView);
        mFirstImageTitle.setText(mImageTitle);
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }

}
