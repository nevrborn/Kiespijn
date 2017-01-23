package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bnnvara.kiespijn.BuildConfig;
import com.bnnvara.kiespijn.Dilemma.Dilemma;
import com.bnnvara.kiespijn.GoogleImageSearch.GoogleSearchActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class CreateDilemmaFragment extends Fragment {

    private static final String TAG = "CreateDilemmaFragment";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_GALLERY = 2;
    private static final String DILEMMA_OBJECT = "dilemma_object";
    private static final String SEARCH_STRING = "search_string";
    private static final String GOOGLE_IMAGE_URL = "google_image_url";
    private static final int GOOGLE_IMAGE = 3;

    private static final int REQUEST_PERMISSIONS = 1000;
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText mOptionAText;
    private EditText mOptionBText;
    private ImageView mImageViewA;
    private ImageView mImageViewB;
    private Boolean isImageA = true;
    private String mCurrentPhotoPath;

    // dilemma variables
    private static Dilemma mDilemma;

    public static Fragment newInstance(Dilemma dilemma) {

        if (mDilemma != null) {
            mDilemma = dilemma;
        } else {
            mDilemma = new Dilemma();
        }

        return new CreateDilemmaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionUtils.requestIfNeeded(getActivity(), PERMISSIONS, REQUEST_PERMISSIONS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dilemma, container, false);

        // set up the references
        EditText dilemmaTitle = (EditText) view.findViewById(R.id.text_view_dilemma_title);
        mOptionAText = (EditText) view.findViewById(R.id.edit_text_option_1);
        mOptionBText = (EditText) view.findViewById(R.id.edit_text_option_2);
        mImageViewA = (ImageView) view.findViewById(R.id.image_view_option_1_take_picture);
        mImageViewB = (ImageView) view.findViewById(R.id.image_view_option_2_take_picture);
        Button nextButton = (Button) view.findViewById(R.id.button_next_create_dilemma);
        ImageView contextButton = (ImageView) view.findViewById(R.id.add_context);


        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        dilemmaTitle.setTypeface(source_sans_bold);
        mOptionAText.setTypeface(source_sans_extra_light);
        mOptionBText.setTypeface(source_sans_extra_light);

        if (mDilemma != null) {
            if (mDilemma.getPhotoA() != null && mDilemma.getPhotoB() != null) {
                dilemmaTitle.setText(mDilemma.getTitle());
                    mOptionAText.setText(mDilemma.getTitlePhotoA());
                    mOptionBText.setText(mDilemma.getTitlePhotoB());

                displayPicture(mDilemma.getPhotoA(), mImageViewA);
                displayPicture(mDilemma.getPhotoB(), mImageViewB);
            }
        }

        contextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContext();
            }
        });

        // set up the listeners
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFieldsFilledIn()) {
                    Intent i = TargetGroupActivity.newIntent(getActivity());
                    i.putExtra(DILEMMA_OBJECT, mDilemma);
                    startActivity(i);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), R.string.not_all_fields_filled, Toast.LENGTH_SHORT).show();
                }

            }
        });

        dilemmaTitle.addTextChangedListener(new TextWatcher() {
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
                isImageA = true;
                selectImage();

            }
        });

        mImageViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageA = false;
                selectImage();
            }
        });

        return view;
    }

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.photo_gallery), getString(R.string.google_search)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.take_photo))) {
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.photo_gallery))) {
                    galleryIntent();
                } else if (items[item].equals(getString(R.string.google_search))) {
                    String searchString;
                    if (isImageA) {
                        searchString = mOptionAText.getText().toString();
                    } else {
                        searchString = mOptionBText.getText().toString();
                    }
                    Intent i = new Intent(GoogleSearchActivity.newIntent(getActivity()));
                    i.putExtra(SEARCH_STRING, searchString);
                    startActivityForResult(i, GOOGLE_IMAGE);
                }
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView
            Uri imageUri = Uri.parse(mCurrentPhotoPath);
            String path = imageUri.getPath();

            if (isImageA) {
                mDilemma.setPhotoA(path);
                displayPicture(path, mImageViewA);
            } else {
                mDilemma.setPhotoB(path);
                displayPicture(path, mImageViewB);
            }

            // ScanFile so it will appear in the device gallery
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{path}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();

            if (isImageA) {
                mDilemma.setPhotoA(selectedImage.toString());
                displayPicture(selectedImage.toString(), mImageViewA);
            } else {
                mDilemma.setPhotoB(selectedImage.toString());
                displayPicture(selectedImage.toString(), mImageViewB);
            }

        }

        if (requestCode == GOOGLE_IMAGE && resultCode == Activity.RESULT_OK) {
            String googleUri = data.getStringExtra(GOOGLE_IMAGE_URL);

            if (isImageA) {
                mDilemma.setPhotoA(googleUri);
                displayPicture(googleUri, mImageViewA);
            } else {
                mDilemma.setPhotoB(googleUri);
                displayPicture(googleUri, mImageViewB);
            }

        }

    }

    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);
    }

    private Boolean isFieldsFilledIn() {

        return !(mDilemma.getTitle() == null || mDilemma.getTitlePhotoA() == null || mDilemma.getTitlePhotoB() == null || mDilemma.getPhotoA() == null || mDilemma.getPhotoB() == null);
    }

    private void addContext() {
        final AlertDialog.Builder contextAlert = new AlertDialog.Builder(getContext());
        final EditText enterContext = new EditText(getContext());
        enterContext.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        contextAlert.setTitle("Enter Dilemma Context");
        contextAlert.setView(enterContext);

        if (mDilemma.getBackgroundInfo() != null) {
            enterContext.setText(mDilemma.getBackgroundInfo());
        }

        contextAlert.setPositiveButton("SAVE CONTEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDilemma.setBackgroundInfo(enterContext.getText().toString());
            }
        });

        contextAlert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        contextAlert.show();
    }

    private void displayPicture(String pictureURL, ImageView imageView) {
        Glide.with(getActivity())
                .load(pictureURL)
                .centerCrop()
                .placeholder(R.drawable.ic_action_sand_timer)
                .into(imageView);
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


}