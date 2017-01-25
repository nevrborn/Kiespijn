package com.bnnvara.kiespijn.Login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bnnvara.kiespijn.DilemmaPage.DilemmaActivity;
import com.bnnvara.kiespijn.FriendList.Friend;
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
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    // Facebook Parameters
    private CallbackManager mCallbackManager;
    private static Boolean mIsLoggingOut = false;
    private static Boolean mHasJustLoggedOut = false;
    private User mUser;
    LoginButton mLoginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(Boolean isLoggingOut) {
        mIsLoggingOut = isLoggingOut;
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this.getContext());
        mCallbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(getActivity().getApplication());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Setting up the different view elements
        TextView title = (TextView) view.findViewById(R.id.textview_login_title);
        TextView subtitle = (TextView) view.findViewById(R.id.textview_login_subtitle);
        TextView text = (TextView) view.findViewById(R.id.textview_login_text);
        mLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);

        // FONT setup
        Typeface source_sans_extra_light = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-ExtraLight.ttf");
        Typeface source_sans_bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Bold.ttf");
        subtitle.setTypeface(source_sans_extra_light);
        text.setTypeface(source_sans_extra_light);

        // Facebook Login Button Setup
        mLoginButton.setReadPermissions("email", "public_profile", "user_friends");
        mLoginButton.setFragment(this);

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                getFacebookParameters();
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
        // TODO: Do we need this? Can we delete it?
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFacebookParameters() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                getFacebookParameters(object);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,hometown,friends{id,name,picture},gender,birthday,picture{url}");

        request.setParameters(parameters);
        request.executeAsync();

    }

    private void getFacebookParameters(JSONObject object) {

        mUser = User.getInstance();

        if (object != null) {

            try {
                String facebookID = object.getString("id");
                Log.i(TAG, "Facebook ID is: " + facebookID);
                String facebookName = object.getString("name");
                Log.i(TAG, "Facebook NAME is: " + facebookName);
                String facebookGender = object.getString("gender");
                Log.i(TAG, "Facebook GENDER is: " + facebookGender);

                mUser.setUserKey(facebookID);
                mUser.setName(facebookName);
                mUser.setSex(facebookGender);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String facebookHometown = null;
            try {
                facebookHometown = object.getString("hometown");
                String hometown = facebookHometown.substring(0, facebookHometown.indexOf(','));
                mUser.setHometown(hometown);
            } catch (JSONException e) {
                mUser.setHometown("Onbekend");
                e.printStackTrace();
            }


            // GET BIRTHDAY IF POSSIBLE
            try {
                String facebookBirthday = object.getString("birthday");
                Log.i(TAG, "Facebook BIRTHDAY is: " + facebookBirthday);
                String age = calculateAge(facebookBirthday);
                mUser.setAge(age);
            } catch (JSONException e) {
                mUser.setAge("Onbekend");
                e.printStackTrace();

            }

            // GET PHOTO URL IF POSSIBLE
            try {
                JSONObject pictureObject = object.getJSONObject("picture");
                JSONObject pictureData = pictureObject.getJSONObject("data");
                String facebookPictureURL = pictureData.getString("url");
                Log.i(TAG, "Facebook PICTURE URL is: " + facebookPictureURL);
                mUser.setProfilePictureURL(facebookPictureURL);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject facebookFriends = object.getJSONObject("friends");
                JSONArray friendsObject = facebookFriends.getJSONArray("data");
                Log.i(TAG, "Facebook Friends Object is: " + friendsObject);

                int friendsCount = friendsObject.length();

                int i = 0;

                if (friendsCount != 0) {

                    mUser.getFacebookFriendList().clear();

                    while (i < friendsCount) {

                        JSONObject friend = friendsObject.getJSONObject(i);
                        String friendID = friend.getString("id");
                        String friendName = friend.getString("name");

                        JSONObject friendPictureObject = friend.getJSONObject("picture");
                        JSONObject friendPictureData = friendPictureObject.getJSONObject("data");
                        String friendFacebookPictureURL = friendPictureData.getString("url");

                        Friend newFriend = new Friend(friendName, friendID, friendFacebookPictureURL);
                        mUser.addFriendToFacebookFriendList(newFriend);

                        i += 1;
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIsLoggingOut = false;
            mHasJustLoggedOut = false;
            Intent i = DilemmaActivity.newIntent(getContext());
            startActivity(i);

        } else {
            getFacebookParameters();
        }

    }

    private String calculateAge(String birthday) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(birthday);
        } catch (ParseException e) {
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
        return String.valueOf(age);

    }

    @Override
    public void onResume() {
        super.onResume();
        ((LoginActivity)getActivity()).hideStatusBar();

        mUser = User.getInstance();

        if (!mIsLoggingOut && isConnected() && !mUser.getUserKey().equals("") && !mHasJustLoggedOut) {
            Intent i = DilemmaActivity.newIntent(getContext());
            startActivity(i);
        } else if (!mIsLoggingOut && isConnected() && mUser.getUserKey().equals("") && !mHasJustLoggedOut) {
            getFacebookParameters();
        } else if (mIsLoggingOut && !mHasJustLoggedOut) {
            mLoginButton.performClick();
            mIsLoggingOut = false;
            mHasJustLoggedOut = true;
        }
    }

    public boolean isConnected() {
        //Checks if the device is connected to the internet (WIFI or mobile data)
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            internetConnectionAlert();
        return false;
    }

    public void internetConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No Internet Connection");
        builder.setMessage("Your device is not connected to the Internet");
        builder.setPositiveButton("WiFi Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent settings = new Intent(Settings.ACTION_WIFI_SETTINGS);
                settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(settings);
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}
