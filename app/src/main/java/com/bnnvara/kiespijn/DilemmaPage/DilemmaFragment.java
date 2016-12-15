package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bnnvara.kiespijn.CreateDilemmaPage.CreateDilemmaActivity;
import com.bnnvara.kiespijn.Dilemma.Answer;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.Dilemma.DilemmaApiResponse;
import com.bnnvara.kiespijn.Dilemma.Replies;
import com.bnnvara.kiespijn.Login.LoginActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.FacebookDialogFragment;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class DilemmaFragment extends Fragment {

    // constants
    private static final String TAG = DialogFragment.class.getSimpleName();

    // Views
    private ImageView mUserPhotoImageView;
    private TextView mUserNameTextView;
    private TextView mUserDescriptionTextView;
    private TextView mDilemmaTextView;
    private ImageView mDilemmaFirstImageView;
    private ImageView mDilemmaSecondImageView;

    // regular variables
    private Dilemma mDilemma;
    private ArrayList<Dilemma> mDilemmaList;


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

        // set up the references
        mUserPhotoImageView = (ImageView) view.findViewById(R.id.image_view_user_photo);
        mUserNameTextView = (TextView) view.findViewById(R.id.text_view_username);
        mUserDescriptionTextView = (TextView) view.findViewById(R.id.text_view_user_info);
        mDilemmaTextView = (TextView) view.findViewById(R.id.text_view_dilemma);
        mDilemmaFirstImageView = (ImageView) view.findViewById(R.id.image_view_first_option_decicision_page);
        mDilemmaSecondImageView = (ImageView) view.findViewById(R.id.image_view_second_option_decicision_page);

        // set up the listeners
        mUserPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data and update UI
                createDummyDate();
                updateUi();
            }
        });

        return view;
    }

    private void updateUi() {
        mDilemma = mDilemmaList.get(0);

        if (mDilemma.getAnonymous() == "0"){
            mUserNameTextView.setText("Get this from the FB User");
            mUserDescriptionTextView.setText("Get this from the FB User");
        } else {
            mUserNameTextView.setText(getString(R.string.dilemma_username));
            mUserDescriptionTextView.setText("Get this from the FB User");
        }

        mDilemmaFirstImageView.setBackground(null);
        mDilemmaSecondImageView.setBackground(null);

        mDilemmaTextView.setText(mDilemma.getTitle());
        Glide.with(getActivity())
                .load(mDilemma.getPhotoA())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(mDilemmaFirstImageView);

        mDilemmaTextView.setText(mDilemma.getTitle());
        Glide.with(getActivity())
                .load(mDilemma.getPhotoB())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(mDilemmaSecondImageView);

    }


    private void createDummyDate() {
        // SET UP TEST DATA!
        Dilemma dilemma_1 = new Dilemma();
        dilemma_1.setTitle("Ik heb bloemen gekregen. Moet ik ze in een mooie vaas stoppen of in een houten kiest?");
        dilemma_1.setUuid();
        dilemma_1.setCreator_fb_id("10156521655410158");
        dilemma_1.setPhotoA("http://s.hswstatic.com/gif/cremation-urn.jpg");
        dilemma_1.setPhotoB("http://www.gayworld.be/wp-content/uploads/2009/10/uitvaart-begrafenis-stephen-gately-300x252.jpg");
        dilemma_1.setDeadline(12);
        dilemma_1.setCreatedAt();
        dilemma_1.setAnonymous("0");
        Replies replies = new Replies();
        List<Answer> option1AnswerList = new ArrayList<>() ;
        List<Answer> option2AnswerList = new ArrayList<>() ;
        option1AnswerList.add(new Answer("awef-2398"));
        option1AnswerList.add(new Answer("erg-rth98"));
        option2AnswerList.add(new Answer("qweasd-999"));
        option2AnswerList.add(new Answer("lkmlk-8930"));
        replies.setOption1AnswerList((ArrayList<Answer>) option1AnswerList);
        replies.setOption2AnswerList((ArrayList<Answer>) option2AnswerList);
        dilemma_1.setReplies(replies);

        List<Dilemma> dilemmaList = new ArrayList<>();
        dilemmaList.add(dilemma_1);
        DilemmaApiResponse dilemmaApiResponse = new DilemmaApiResponse();
        dilemmaApiResponse.setDilemmaList(dilemmaList);
        mDilemmaList = (ArrayList<Dilemma>) dilemmaApiResponse.getDilemmaList();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_general, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_user:
                Intent i = LoginActivity.newIntent(getActivity());
                startActivity(i);
                return true;
            case R.id.menu_item_create_dilemma:
                Intent intent = CreateDilemmaActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
            default: return true;
        }
    }
}