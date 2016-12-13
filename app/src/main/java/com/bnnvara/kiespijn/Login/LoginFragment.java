package com.bnnvara.kiespijn.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bnnvara.kiespijn.R;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    // Parameters
    private String mEmail;
    private String mPassword;

    // Firebase Parameters
    private FirebaseAuth mFirebaseAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Setting up the different view elements
        Button facebookButton = (Button) view.findViewById(R.id.button_login_facebook);
        Button googleButton = (Button) view.findViewById(R.id.button_login_google);
        EditText emailField = (EditText) view.findViewById(R.id.edittext_login_email);
        EditText passwordField = (EditText) view.findViewById(R.id.edittext_login_password);



        return view;
    }

}
