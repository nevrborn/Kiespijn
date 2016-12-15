package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.bnnvara.kiespijn.Dilemma.Option;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;

import static android.app.Activity.RESULT_OK;


public class CreateDilemmaFragment extends Fragment {

    private static final String TAG = "CreateDilemmaFragment";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final String DILEMMA_OBJECT = "dilemma_object";

    // views
    private EditText mDilemmaTitle;
    private EditText mOption1Text;
    private EditText mOption2Text;
    //    private SearchView mSearchView1;
//    private SearchView mSearchView2;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private Bitmap mImage1;
    private Bitmap mImage2;
    private Button mNextButton;
    private Boolean isImage1 = true;

    // dilemma variables
    private Dilemma mDilemma;
    private Option mOption1;
    private Option mOption2;

    public static Fragment newInstance() {
        return new CreateDilemmaFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dilemma, container, false);

        mDilemma = new Dilemma();
        mOption1 = new Option();
        mOption2 = new Option();

        // set up the references
        mDilemmaTitle = (EditText) view.findViewById(R.id.text_view_dilemma_title);
        mOption1Text = (EditText) view.findViewById(R.id.edit_text_option_1);
        mOption2Text = (EditText) view.findViewById(R.id.edit_text_option_2);
//        mSearchView1 = (SearchView) view.findViewById(R.id.search_view_option_1_search_image);
//        mSearchView2 = (SearchView) view.findViewById(R.id.search_view_option_2_search_image);
        mImageView1 = (ImageView) view.findViewById(R.id.image_view_option_1_take_picture);
        mImageView2 = (ImageView) view.findViewById(R.id.image_view_option_2_take_picture);
        mNextButton = (Button) view.findViewById(R.id.button_next_create_dilemma);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        mDilemmaTitle.setTypeface(source_sans_bold);
        mOption1Text.setTypeface(source_sans_extra_light);
        mOption2Text.setTypeface(source_sans_extra_light);

        // set up the listeners
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDilemma.setOption1(mOption1);
                mDilemma.setOption2(mOption2);

                Log.i(TAG, mDilemma.getTitle());
                Log.i(TAG, mDilemma.getOption1().getTitle());
                Log.i(TAG, mDilemma.getOption2().getTitle());


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

        mOption1Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // leave blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mOption1.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // leave blank
            }
        });

        mOption2Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // leave blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mOption2.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // leave blank
            }
        });

        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCameraSupport()) {
                    takePictureIntent();
                    isImage1 = true;
                } else {
                    Toast.makeText(getContext(), "Device has no camera or app is not allowed to use it", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hasCameraSupport()) {
                    takePictureIntent();
                    isImage1 = false;
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

            if (isImage1) {
                mImage1 = imageBitmap;
                mImageView1.setImageBitmap(mImage1);
                mOption1.setImage(mImage1);
            } else {
                mImage2 = imageBitmap;
                mImageView2.setImageBitmap(mImage2);
                mOption2.setImage(mImage2);
            }
        }
    }
}