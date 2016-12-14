package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;


/**
 *
 */
public class CreateDilemmaFragment extends Fragment {

    // views
    private EditText mDilemmaTitle;
    private EditText mOptionAText;
    private EditText mOptionBText;
    private SearchView mSearchViewA;
    private SearchView mSearchViewB;
    private ImageView mImageA;
    private ImageView mImageB;
    private Button mNextButton;


    public static Fragment newInstance() {
        return new CreateDilemmaFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dilemma, container, false);

        // set up the references
        mDilemmaTitle = (EditText) view.findViewById(R.id.text_view_dilemma_title);
        mOptionAText = (EditText) view.findViewById(R.id.edit_text_option_1);
        mOptionBText = (EditText) view.findViewById(R.id.edit_text_option_2);
        mSearchViewA = (SearchView) view.findViewById(R.id.search_view_option_1_search_image);
        mSearchViewB = (SearchView) view.findViewById(R.id.search_view_option_2_search_image);
        mImageA = (ImageView) view.findViewById(R.id.image_view_option_1_take_picture);
        mImageB = (ImageView) view.findViewById(R.id.image_view_option_2_take_picture);
        mNextButton = (Button) view.findViewById(R.id.button_next_create_dilemma);

        // set up the listeners
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = TargetGroupActivity.newIntent(getActivity());
                startActivity(i);
            }
        });


        return view;
    }
}