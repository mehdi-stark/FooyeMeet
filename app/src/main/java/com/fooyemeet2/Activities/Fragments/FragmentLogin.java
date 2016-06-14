package com.fooyemeet2.Activities.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fooyemeet2.Activities.Requests.AsynctaskFacebook;
import com.fooyemeet2.Activities.Requests.AsynctaskLogin;
import com.fooyemeet2.R;

/**
 * Created by Mehdi on 29/02/2016.
 */
public class FragmentLogin extends Fragment {

    public static  FragmentLogin newInstance(){
        FragmentLogin fragmentLogin = new FragmentLogin();
        Bundle args = new Bundle();
        return fragmentLogin;
    }

    private EditText login = null;
    private EditText password = null;
    private View rootView;

    private CallbackManager mCallbackManager = null;
    private AccessToken accessToken = null;
    private AccessTokenTracker accessTokenTracker = null;
    private ProfileTracker profileTracker = null;
    private Profile profile = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplication());
        mCallbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (rootView = inflater.inflate(R.layout.tab1__sign_in, container, false));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button connexion = (Button) rootView.findViewById(R.id.login_button2);
        login = (EditText) rootView.findViewById(R.id.emailEdit);
        password = (EditText) rootView.findViewById(R.id.PasswordEdit);

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginstr = login.getText().toString();
                String passwordstr = password.getText().toString();
               /* if (loginstr.isEmpty()|| passwordstr.isEmpty()){
                    Toast.makeText(getActivity(), "Login or Password incorrect", Toast.LENGTH_SHORT).show();
                }
                else {*/
                AsynctaskLogin async = new AsynctaskLogin(getActivity());
                async.execute(loginstr, passwordstr);
            }
        });

        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.google.fr";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        LoginButton loginButton = (LoginButton) rootView.findViewById(R.id.login_buttonFB);
        loginButton.setReadPermissions("user_friends", "public_profile", "email");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profile = Profile.getCurrentProfile();
                accessToken = AccessToken.getCurrentAccessToken();
                String access = String.valueOf(accessToken);
                String toke = accessToken.getToken();
                //AsynctaskFacebook asynctaskFacebook = new AsynctaskFacebook(getActivity());
                //asynctaskFacebook.execute(toke, access);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        profile = Profile.getCurrentProfile();
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AccessToken.getCurrentAccessToken() != null) {
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


