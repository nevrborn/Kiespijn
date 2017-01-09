package com.bnnvara.kiespijn.AddContentPage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.R;

public class AddContentFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private static Dilemma mDilemma;
    private static String mAnswerOption;

    private String mLink;

    public static Fragment newInstance(Dilemma dilemma, String answerOption) {
        mDilemma = dilemma;
        mAnswerOption = answerOption;
        return new AddContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_content, container, false);

        TextView dilemmaTitle = (TextView) view.findViewById(R.id.textview_add_content_dilemma);
        TextView answerText = (TextView) view.findViewById(R.id.textview_add_content_answer);
        EditText editTextField = (EditText) view.findViewById(R.id.edittext_add_content);
        Button photoButton = (Button) view.findViewById(R.id.button_add_photo);
        Button linkButton = (Button) view.findViewById(R.id.button_add_article);
        Button addButton = (Button) view.findViewById(R.id.button_add_content);
        Button cancelButton = (Button) view.findViewById(R.id.button_add_content_cancel);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        dilemmaTitle.setTypeface(source_sans_extra_light);
        answerText.setTypeface(source_sans_bold);
        editTextField.setTypeface(source_sans_extra_light);
        photoButton.setTypeface(source_sans_extra_light);
        linkButton.setTypeface(source_sans_extra_light);
        addButton.setTypeface(source_sans_bold);
        cancelButton.setTypeface(source_sans_bold);

        dilemmaTitle.setText(mDilemma.getTitle());

        if (mAnswerOption.equals("optionA")) {
            answerText.setText(mDilemma.getTitlePhotoA());
        }
        if (mAnswerOption.equals("optionB")) {
            answerText.setText(mDilemma.getTitlePhotoB());
        }

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLink();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return view;
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Google Search", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo or Video!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Open Camera")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private boolean hasCameraSupport() {
        boolean hasSupport = false;
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            hasSupport = true;
        }
        return hasSupport;
    }

    private void cameraIntent() {
        if (hasCameraSupport()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);
    }

    private void addLink() {
        AlertDialog.Builder linkAlert = new AlertDialog.Builder(getContext());
        final EditText enterLink = new EditText(getContext());

        linkAlert.setMessage("Copy in link to article or blog");
        linkAlert.setTitle("Article / Link");
        linkAlert.setView(enterLink);

        linkAlert.setPositiveButton("SAVE LINK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mLink = enterLink.getText().toString();
            }
        });

        linkAlert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        linkAlert.show();
    }
}
