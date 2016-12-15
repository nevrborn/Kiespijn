package com.bnnvara.kiespijn.DilemmaPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


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
        mDilemmaFirstImageView = (ImageView) view.findViewById(R.id.image_view_choose_left);
        mDilemmaSecondImageView = (ImageView) view.findViewById(R.id.image_view_choose_right);

        // set up the listeners


        // get data and update UI
        createDummyDate();
        updateUi();

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

        mDilemmaTextView.setText(mDilemma.getTitle());
        Glide.with(getActivity())
                .load(mDilemma.getPhotoA())
                .placeholder(R.mipmap.ic_launcher)
                .into(mDilemmaFirstImageView);

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