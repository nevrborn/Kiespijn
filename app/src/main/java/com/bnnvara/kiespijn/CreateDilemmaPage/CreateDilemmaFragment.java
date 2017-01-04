package com.bnnvara.kiespijn.CreateDilemmaPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.bnnvara.kiespijn.GoogleImageSearch.GalleryItem;
import com.bnnvara.kiespijn.GoogleImageSearch.GoogleApiRestInterface;
import com.bnnvara.kiespijn.GoogleImageSearch.GoogleImageApiResponse;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.TargetGroup.TargetGroupActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;


public class CreateDilemmaFragment extends Fragment {

    private static final String TAG = "CreateDilemmaFragment";

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    static final String DILEMMA_OBJECT = "dilemma_object";

    // views
    private EditText mDilemmaTitle;
    private EditText mOptionAText;
    private EditText mOptionBText;
    private ImageView mImageViewA;
    private ImageView mImageViewB;
    private Bitmap mImageA;
    private Bitmap mImageB;
    private Button mNextButton;
    private Boolean isImageA = true;
    public static Boolean isFromCamera = false;

    List<String> urlList = new ArrayList<>();
    private static List<GalleryItem> mGalleryItems;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_dilemma, container, false);

        // set up the references
        mDilemmaTitle = (EditText) view.findViewById(R.id.text_view_dilemma_title);
        mOptionAText = (EditText) view.findViewById(R.id.edit_text_option_1);
        mOptionBText = (EditText) view.findViewById(R.id.edit_text_option_2);
        mImageViewA = (ImageView) view.findViewById(R.id.image_view_option_1_take_picture);
        mImageViewB = (ImageView) view.findViewById(R.id.image_view_option_2_take_picture);
        mNextButton = (Button) view.findViewById(R.id.button_next_create_dilemma);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        mDilemmaTitle.setTypeface(source_sans_bold);
        mOptionAText.setTypeface(source_sans_extra_light);
        mOptionBText.setTypeface(source_sans_extra_light);

        if (mDilemma != null) {
            if (mDilemma.getPhotoA() != null && mDilemma.getPhotoB() != null) {
                try {
                    mDilemmaTitle.setText(mDilemma.getTitle());
                    mOptionAText.setText(mDilemma.getTitlePhotoA());
                    mOptionBText.setText(mDilemma.getTitlePhotoB());
                    mImageViewA.setImageBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(mDilemma.getPhotoA())));
                    mImageViewB.setImageBitmap(MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.parse(mDilemma.getPhotoB())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // set up the listeners
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isFieldsFilledIn()) {
                    Intent i = TargetGroupActivity.newIntent(getActivity());
                    i.putExtra(DILEMMA_OBJECT, mDilemma);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), R.string.not_all_fields_filled, Toast.LENGTH_SHORT).show();
                }

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
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Google Search",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Google Search")) {
                    googleImageSearch();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean hasCameraSupport() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if (isImageA) {
                mImageA = imageBitmap;
                mImageViewA.setImageBitmap(mImageA);
                String imageAUri = getImageUri(getContext(), mImageA).toString();
                mDilemma.setPhotoA(imageAUri);
                Log.i(TAG, imageAUri);
            } else {
                mImageB = imageBitmap;
                mImageViewB.setImageBitmap(mImageB);
                String imageBUri = getImageUri(getContext(), mImageB).toString();
                mDilemma.setPhotoB(imageBUri);
                Log.i(TAG, imageBUri);
            }
        }

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK
                && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            if (isImageA) {
                mImageA = bitmap;
                mImageViewA.setImageBitmap(bitmap);
                mDilemma.setPhotoA(selectedImage.toString());
                Log.i(TAG, selectedImage.toString());
            } else {
                mImageB = bitmap;
                mImageViewB.setImageBitmap(bitmap);
                mDilemma.setPhotoB(selectedImage.toString());
                Log.i(TAG, selectedImage.toString());
            }

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_GALLERY);
    }

    public Boolean isFieldsFilledIn() {

        return !mDilemma.getTitle().equals("") && !mDilemma.getTitlePhotoA().equals("") && !mDilemma.getTitlePhotoB().equals("");

    }

    public void googleImageSearch() {

        String searchString;
        String key = "AIzaSyDNtEcEBu5G3341BkSjJqoOeUqID9MLNp4";
        String cx = "005303562240230618745:fehybwiv3j0";

        if (isImageA) {
            searchString = mOptionAText.getText().toString();
        } else {
            searchString = mOptionBText.getText().toString();
        }

        // Logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GoogleApiRestInterface apiResponse = restAdapter.create(GoogleApiRestInterface.class);

        Call<GoogleImageApiResponse> mGalleryResponse = apiResponse.customSearch(key, cx, searchString);

        mGalleryResponse.enqueue(new Callback<GoogleImageApiResponse>() {
            @Override
            public void onResponse(Call<GoogleImageApiResponse> call, Response<GoogleImageApiResponse> response) {
                GoogleImageApiResponse mGoogleImageApiResponse = response.body();

                if (response.body() == null) {
                    Log.e("Retrofit body null", String.valueOf(response.code()));
                }

                mGalleryItems = mGoogleImageApiResponse.getGalleryItems();
            }

            @Override
            public void onFailure(Call<GoogleImageApiResponse> call, Throwable t) {
                Log.e("Retrofit error", t.getMessage());
            }
        });

    }

}