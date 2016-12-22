package com.bnnvara.kiespijn.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.R;
import com.bnnvara.kiespijn.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    // Facebook Parameters
    private CallbackManager mCallbackManager;
    private String mFacebookID;
    private String mFacebookName;
    private String mFacebookGender;
    private String mFacebookBirthday;
    private String mFacebookPictureURL;
    private JSONObject mFacebookFriends;

    private Map<String, String> mFacebookFriendsMap = new HashMap<>();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getContext());
        mCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getActivity().getApplication());

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        if (isLoggedIn()) {
            getFacebookParameters();
            Intent i = DilemmaActivity.newIntent(getContext());
            startActivity(i);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Setting up the different view elements
        TextView title = (TextView) view.findViewById(R.id.textview_login_title);
        TextView text = (TextView) view.findViewById(R.id.textview_login_text);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_extra_light_italic = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLightItalic.ttf");
        Typeface source_sans_regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
        title.setTypeface(source_sans_extra_light);
        text.setTypeface(source_sans_extra_light);

        // Facebook Login Button Setup
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
        loginButton.setFragment(this);

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        getFacebookParameters();
        Intent i = DilemmaActivity.newIntent(getContext());
        startActivity(i);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
    }

    public void signOut() {
        LoginManager.getInstance().logOut();
    }

    public void getFacebookParameters() {

        AccessToken.refreshCurrentAccessTokenAsync();

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                getFacebookParameters(object);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,friends,gender,birthday,picture{url}");

        request.setParameters(parameters);
        Log.i(TAG, parameters.toString());
        Log.i(TAG, AccessToken.getCurrentAccessToken().getToken());
        request.executeAsync();

    }

    public void getFacebookParameters(JSONObject object) {

        User user = User.getInstance();

        try {
            mFacebookID = object.getString("id");
            Log.i(TAG, "Facebook ID is: " + mFacebookID);
            mFacebookName = object.getString("name");
            Log.i(TAG, "Facebook NAME is: " + mFacebookName);
            mFacebookGender = object.getString("gender");
            Log.i(TAG, "Facebook GENDER is: " + mFacebookGender);

            user.setUserKey(mFacebookID);
            user.setName(mFacebookName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // GET BIRTHDAY IF POSSIBLE
        try {
            mFacebookBirthday = object.getString("birthday");
            Log.i(TAG, "Facebook BIRTHDAY is: " + mFacebookBirthday);
            String age = calculateAge(mFacebookBirthday);
            user.setAge(age);
        } catch (JSONException e) {
            user.setAge("Onbekend");
            e.printStackTrace();

        }

        // GET PHOTO URL IF POSSIBLE
        try {
            JSONObject pictureObject = object.getJSONObject("picture");
            JSONObject pictureData = pictureObject.getJSONObject("data");
            mFacebookPictureURL = pictureData.getString("url");
            Log.i(TAG, "Facebook PICTURE URL is: " + mFacebookPictureURL);
            user.setProfilePictureURL(mFacebookPictureURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            mFacebookFriends = object.getJSONObject("friends");

            JSONArray friendsObject = mFacebookFriends.getJSONArray("data");
            Log.i(TAG, "Facebook Friends Object is: " + friendsObject);

            int friendsCount = friendsObject.length();

            int i = 0;

            if (friendsCount != 0) {
                while (i < friendsCount) {

                    JSONObject friend = friendsObject.getJSONObject(i);
                    String name = friend.getString("name");
                    String id = friend.getString("id");
                    mFacebookFriendsMap.put(name, id);

                    i += 1;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void postToFacebook() {

    }

    public String calculateAge(String birthday) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(birthday);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        birthDate.setTime(convertedDate);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of
        // leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR)
                - today.get(Calendar.DAY_OF_YEAR) > 3)
                || (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of
            // month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                && (birthDate.get(Calendar.DAY_OF_MONTH) > today
                .get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        String ageString = String.valueOf(age);
        return ageString;

    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}
