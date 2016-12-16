package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static android.app.Activity.RESULT_OK;


public class CreateDilemmaFragment extends Fragment {

    private static final String TAG = "CreateDilemmaFragment";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String DILEMMA_OBJECT = "dilemma_object";
    static final String DILEMMA_PHOTO_A = "dilemma_photo_a";
    static final String DILEMMA_PHOTO_B = "dilemma_photo_b";

    // views
    private EditText mDilemmaTitle;
    private EditText mOptionAText;
    private EditText mOptionBText;
    //    private SearchView mSearchView1;
//    private SearchView mSearchView2;
    private ImageView mImageViewA;
    private ImageView mImageViewB;
    private Bitmap mImageA;
    private Bitmap mImageB;
    private Button mNextButton;
    private Boolean isImageA = true;

    // dilemma variables
    private Dilemma mDilemma;

    public static Fragment newInstance() {
        return new CreateDilemmaFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dilemma, container, false);

        mDilemma = new Dilemma();

        // set up the references
        mDilemmaTitle = (EditText) view.findViewById(R.id.text_view_dilemma_title);
        mOptionAText = (EditText) view.findViewById(R.id.edit_text_option_1);
        mOptionBText = (EditText) view.findViewById(R.id.edit_text_option_2);
//        mSearchView1 = (SearchView) view.findViewById(R.id.search_view_option_1_search_image);
//        mSearchView2 = (SearchView) view.findViewById(R.id.search_view_option_2_search_image);
        mImageViewA = (ImageView) view.findViewById(R.id.image_view_option_1_take_picture);
        mImageViewB = (ImageView) view.findViewById(R.id.image_view_option_2_take_picture);
        mNextButton = (Button) view.findViewById(R.id.button_next_create_dilemma);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        mDilemmaTitle.setTypeface(source_sans_bold);
        mOptionAText.setTypeface(source_sans_extra_light);
        mOptionBText.setTypeface(source_sans_extra_light);

        // set up the listeners
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, mDilemma.getTitle());

                Intent i = TargetGroupActivity.newIntent(getActivity());
                i.putExtra(DILEMMA_OBJECT, mDilemma);
                startActivity(i);
            }
        });

        mDilemmaTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // leave blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDilemma.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // leave blank
            }
        });

        mOptionAText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // leave blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDilemma.setTitlePhotoA(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // leave blank
            }
        });

        mOptionBText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // leave blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDilemma.setTitlePhotoB(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // leave blank
            }
        });

        mImageViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCameraSupport()) {
                    takePictureIntent();
                    isImageA = true;
                } else {
                    Toast.makeText(getContext(), "Device has no camera or app is not allowed to use it", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mImageViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCameraSupport()) {
                    takePictureIntent();
                    isImageA = false;
                } else {
                    Toast.makeText(getContext(), "Device has no camera or app is not allowed to use it", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public boolean hasCameraSupport() {
        boolean hasSupport = false;
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            hasSupport = true;
        }
        return hasSupport;
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (isImageA) {
                mImageA = imageBitmap;
                mImageViewA.setImageBitmap(mImageA);
                mDilemma.setPhotoA("https://farm1.staticflickr.com/379/30812017803_a62730715e_m.jpg"); // DUMMY VALUE
            } else {
                mImageB = imageBitmap;
                mImageViewB.setImageBitmap(mImageB);
                mDilemma.setPhotoB("https://goo.gl/lsKFBk"); // DUMMY VALUE
            }
        }

    }
}