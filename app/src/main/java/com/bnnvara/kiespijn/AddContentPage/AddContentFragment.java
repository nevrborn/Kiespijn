package com.bnnvara.kiespijn.AddContentPage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bnnvara.kiespijn.ArticleSearchPage.ArticleSearchActivity;
import com.bnnvara.kiespijn.BuildConfig;
import com.bnnvara.kiespijn.ContentPage.Content;
import com.bnnvara.kiespijn.CreateDilemmaPage.PermissionUtils;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.GoogleImageSearch.GoogleSearchActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class AddContentFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final int GOOGLE_IMAGE = 3;
    private static final int REQUEST_ARTICLE_URL = 4;
    private static final String SEARCH_STRING = "search_string";
    private static final String GOOGLE_IMAGE_URL = "google_image_url";
    private static final String ARTICLE_URL = "article_url";

    private static final int REQUEST_PERMISSIONS = 1000;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static Dilemma mDilemma;
    private static String mAnswerOption;
    private boolean mIsAPhoto;
    private boolean mIsAnArticle;
    private String mCurrentPhotoPath;

    private String mImageLink;
    private String mArticleLink;
    private LinearLayout mAddedContentLayout;

    private TextView mLinkView;
    private ImageView mImageThumbnail;


    public static Fragment newInstance(Dilemma dilemma, String answerOption) {
        mDilemma = dilemma;
        mAnswerOption = answerOption;
        return new AddContentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        PermissionUtils.requestIfNeeded(getActivity(), PERMISSIONS, REQUEST_PERMISSIONS);
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
        mLinkView = (TextView) view.findViewById(R.id.add_content_link_view);
        mImageThumbnail = (ImageView) view.findViewById(R.id.imageview_thumbnail);
        mAddedContentLayout = (LinearLayout) view.findViewById(R.id.linear_layout_added_content);

        setHasOptionsMenu(true);

        mImageThumbnail.setVisibility(View.GONE);
        mLinkView.setVisibility(View.GONE);
        mAddedContentLayout.setVisibility(View.GONE);

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
        mLinkView.setTypeface(source_sans_bold);

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
                startActivityForResult(i, GOOGLE_IMAGE);
                mIsAPhoto = true;
            }
        });

        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = ArticleSearchActivity.newIntent(getActivity());
                startActivityForResult(i, REQUEST_ARTICLE_URL);
                mIsAnArticle = true;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getInstance();
                String text = editTextField.getText().toString();

                Content content = new Content(text, mIsAPhoto, mIsAnArticle, user.getName(), user.getUserKey(), user.getAge(), user.getSex(), user.getProfilePictureURL(), user.getHometown());

                if (mIsAPhoto) {
                    content.setPhotoLink(mImageLink);
                } else {
                    content.setArticleUrl(mArticleLink);
                }

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
                infoDialog.setTitle(R.string.add_content_purpose_title);
                infoDialog.setMessage(R.string.add_content_purpose_text);
                infoDialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
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
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.photo_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.add_photo));
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.photo_gallery))) {
                    galleryIntent();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            mImageLink = imageUri.getPath();

            Glide.with(getActivity())
                    .load(mImageLink)
                    .into(mImageThumbnail);

            mImageThumbnail.setVisibility(View.VISIBLE);
            mAddedContentLayout.setVisibility(View.VISIBLE);

            // ScanFile so it will appear in the device gallery
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{mImageLink}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            mImageLink = selectedImage.toString();

            Glide.with(getActivity())
                    .load(mImageLink)
                    .into(mImageThumbnail);

            mImageThumbnail.setVisibility(View.VISIBLE);
            mAddedContentLayout.setVisibility(View.VISIBLE);

        }

        if (requestCode == REQUEST_ARTICLE_URL && resultCode == Activity.RESULT_OK) {
            mLinkView.setText(data.getStringExtra(ARTICLE_URL));
            mArticleLink = data.getStringExtra(ARTICLE_URL);
            mLinkView.setVisibility(View.VISIBLE);
            mAddedContentLayout.setVisibility(View.VISIBLE);
        }

        if (requestCode == GOOGLE_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri googleUri = Uri.parse(data.getStringExtra(GOOGLE_IMAGE_URL));

            Glide.with(getActivity())
                    .load(googleUri)
                    .into(mImageThumbnail);

            mImageThumbnail.setVisibility(View.VISIBLE);
            mAddedContentLayout.setVisibility(View.VISIBLE);

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            // Confirm required permissions have been granted
            boolean hasPermissions = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    // Cannot proceed without permissions
                    hasPermissions = false;
                    openUpSettings();
                }
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openUpSettings() {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }

}
