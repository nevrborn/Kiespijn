package com.bnnvara.kiespijn.DilemmaPage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnnvara.kiespijn.R;


/**
 *
 */
public class DilemmaFragment extends Fragment {

    // Views
    private ImageView mUserPhotoImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private ImageView mDilemmaFirstImageView;
    private ImageView mDilemmaSecondImageView;


    public static Fragment newInstance() {
        return new DilemmaFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_decision_page, container, false);

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_dilemma);
        mDilemmaFirstImageView = (ImageView) view.findViewById(R.id.image_view_choose_left);
        mDilemmaSecondImageView = (ImageView) view.findViewById(R.id.image_view_choose_right);

        // set up the listeners


        return view;
    }
}