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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bnnvara.kiespijn.ContentPage.Content;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.GoogleImageSearch.GoogleSearchActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;

public class AddContentFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;

    private static final String SEARCH_STRING = "search_string";

    private static Dilemma mDilemma;
    private static String mAnswerOption;
    private Boolean mIsAPhoto;

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
        final TextView answerText = (TextView) view.findViewById(R.id.textview_add_content_answer);
        final EditText editTextField = (EditText) view.findViewById(R.id.edittext_add_content);
        Button takePhotoButton = (Button) view.findViewById(R.id.button_add_photo);
        Button googleButton = (Button) view.findViewById(R.id.button_add_google);
        Button linkButton = (Button) view.findViewById(R.id.button_add_article);
        Button addButton = (Button) view.findViewById(R.id.button_add_content);

        setHasOptionsMenu(true);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        dilemmaTitle.setTypeface(source_sans_extra_light);
        answerText.setTypeface(source_sans_bold);
        editTextField.setTypeface(source_sans_extra_light);
        takePhotoButton.setTypeface(source_sans_extra_light);
        googleButton.setTypeface(source_sans_extra_light);
        linkButton.setTypeface(source_sans_extra_light);
        addButton.setTypeface(source_sans_bold);

        dilemmaTitle.setText(mDilemma.getTitle());

        if (mAnswerOption.equals("optionA")) {
            answerText.setText(mDilemma.getTitlePhotoA());
        }
        if (mAnswerOption.equals("optionB")) {
            answerText.setText(mDilemma.getTitlePhotoB());
        }

        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                mIsAPhoto = true;
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = answerText.getText().toString();
                Intent i = new Intent(GoogleSearchActivity.newIntent(getActivity()));
                i.putExtra(SEARCH_STRING, searchString);
                startActivity(i);
                mIsAPhoto = true;
            }
        });

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLink();
                mIsAPhoto = false;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getInstance();
                String text = editTextField.getText().toString();

                Content content = new Content(text, mIsAPhoto, user.getName(), user.getUserKey(), user.getAge(), user.getSex(), user.getProfilePictureURL(), user.getHometown());

                if (mAnswerOption.equals("optionA")) {
                    mDilemma.getContents().addContentToOptionA(content);
                }
                if (mAnswerOption.equals("optionB")) {
                    mDilemma.getContents().addContentToOptionB(content);
                }

                getActivity().finish();
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_content, menu);

        MenuItem infoItem = menu.findItem(R.id.menu_item_info);

        infoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final AlertDialog.Builder infoDialog = new AlertDialog.Builder(getContext());
                infoDialog.setTitle("Info");
                infoDialog.setMessage("INFO TO COME LATER");
                infoDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                infoDialog.show();

                return false;
            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
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
