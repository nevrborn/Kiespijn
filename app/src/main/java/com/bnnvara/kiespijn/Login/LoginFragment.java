package com.bnnvara.kiespijn.Login;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    // Initialising UI elements
    private Button mFacebookButton;
    private Button mGoogleButton;
    private EditText mEmailField;
    private EditText mPassowrdField;

    // Parameters
    private String mEmail;
    private String mPassword;

    // Firebase Parameters

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
}
