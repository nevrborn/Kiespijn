package com.bnnvara.kiespijn.Deadline;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.DilemmaFromWho.DilemmaFromWhoActivity;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.DilemmaPage.DilemmaFragment;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.ResultPage.ResultActivity;
import com.bnnvara.kiespijn.User;

public class DeadlineFragment extends Fragment {

    private static final String TAG = "DeadlineFragment";
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final int SHARE_DILEMMA = 0;

    private static Dilemma mDilemma;
    private int mDeadline;
    private Boolean isHasChosen = false;

    public static DeadlineFragment newInstance(Dilemma dilemma) {
        mDilemma = dilemma;
        return new DeadlineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deadline, container, false);

        final TextView title = (TextView) view.findViewById(R.id.textview_deadline_title);
        final Button hourButton1 = (Button) view.findViewById(R.id.button_deadline_hours_1);
        final Button hourButton2 = (Button) view.findViewById(R.id.button_deadline_hours_2);
        final Button hourButton3 = (Button) view.findViewById(R.id.button_deadline_hours_3);
        Button previousButton = (Button) view.findViewById(R.id.button_previous_deadline);
        //Button postDilemmaButton = (Button) view.findViewById(R.id.button_next_deadline);
        TextView postDilemmaButton = (TextView) view.findViewById(R.id.textview_post_dilemma);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        title.setTypeface(source_sans_bold);
        hourButton1.setTypeface(source_sans_extra_light);
        hourButton2.setTypeface(source_sans_extra_light);
        hourButton3.setTypeface(source_sans_extra_light);
        postDilemmaButton.setTypeface(source_sans_bold);


        if (mDilemma.getDeadline() != 0) {

            isHasChosen = true;
            long deadline = (mDilemma.getDeadline() - mDilemma.getCreatedAt()) * 1000L;
            long timeToAdd = deadline / 3600000;
            mDeadline = (int) timeToAdd;

            if (mDeadline == 2) {
                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton1.setTextColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setTextColor(getResources().getColor(R.color.colorYellow));
            } else if (mDeadline == 12) {
                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton1.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setTextColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setTextColor(getResources().getColor(R.color.colorYellow));
            } else if (mDeadline == 24) {
                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton1.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setTextColor(getResources().getColor(R.color.colorGreen));
            }


            Log.i(TAG, "CreatedAt: " + mDilemma.getDateAndTime(mDilemma.getCreatedAt()));
            Log.i(TAG, "Deadline: " + mDilemma.getDateAndTime(mDilemma.getDeadline()));

        } else {

        }

        hourButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeadline = 2;
                isHasChosen = true;

                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton1.setTextColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setTextColor(getResources().getColor(R.color.colorYellow));
            }
        });

        hourButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeadline = 12;
                isHasChosen = true;

                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton1.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setTextColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton3.setTextColor(getResources().getColor(R.color.colorYellow));
            }
        });

        hourButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeadline = 24;
                isHasChosen = true;

                hourButton1.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton1.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton2.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                hourButton2.setTextColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                hourButton3.setTextColor(getResources().getColor(R.color.colorGreen));
            }
        });


        postDilemmaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHasChosen) {
                    postDilemma();
                    shareDilemma();
                } else {
                    Toast.makeText(getActivity(), R.string.check_at_least_one_deadline, Toast.LENGTH_SHORT).show();
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateCreatedAndDeadLine(mDeadline);
                Intent i = DilemmaFromWhoActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }


    private void setDateCreatedAndDeadLine(int deadline) {
        mDilemma.setCreatedAt();
        mDilemma.setDeadline(deadline);

    }

    private void setUserDetails() {
        User user = User.getInstance();
        mDilemma.setCreator_fb_id(user.getUserKey());
        mDilemma.setCreator_name(user.getName());
        mDilemma.setCreator_sex(user.getSex());
        mDilemma.setCreator_age(user.getAge());
        mDilemma.setCreator_picture_url(user.getProfilePictureURL());
    }

    private void postDilemma() {
        setUserDetails();
        setDateCreatedAndDeadLine(mDeadline);
        mDilemma.setUuid();

        //SOME CODE TO POST DILEMMA TO DATABASE
        Log.i(TAG, " DILLEMMA INFORMATION");
        Log.i(TAG, " Title: " + mDilemma.getTitle());
        Log.i(TAG, " Facebook ID: " + mDilemma.getCreator_fb_id());
        Log.i(TAG, " PhotoA Title: " + mDilemma.getTitlePhotoA());
        Log.i(TAG, " PhotoA Uti: " + mDilemma.getPhotoA());
        Log.i(TAG, " PhotoB Title: " + mDilemma.getTitlePhotoB());
        Log.i(TAG, " PhotoB Uri: " + mDilemma.getPhotoB());
        Log.i(TAG, " Is To All: " + mDilemma.getIsToAll());
        Log.i(TAG, " Is Anonynous: " + mDilemma.getIsAnonymous());
        Log.i(TAG, " Created At: " + mDilemma.getDateAndTime(mDilemma.getCreatedAt()));
        Log.i(TAG, " Deadline: " + mDilemma.getDateAndTime(mDilemma.getDeadline()));

        //DilemmaFragment.addDilemmaToTempList(mDilemma);

    }

    private void shareDilemma() {
        AlertDialog shareDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.share_on_social_media))
                .setMessage(getString(R.string.share_on_social_media_text))
                .setPositiveButton(getString(R.string.share), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                        intent.putExtra(Intent.EXTRA_TEXT, "HALPP! Wat moet ik kiezen? " + mDilemma.getTitlePhotoA() + " of " + mDilemma.getTitlePhotoB());
                        startActivityForResult(intent, SHARE_DILEMMA);
                    }
                })
                .setNegativeButton(getString(R.string.go_to_main), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToMain();
                    }
                })
                .show();
    }

    private void goToMain() {
        mDilemma = null;
        getActivity().finish();
        Toast.makeText(getActivity(), R.string.dilemma_is_posted, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SHARE_DILEMMA && resultCode == ResultActivity.RESULT_CANCELED) {
            shareDilemma();
        }

    }
}
