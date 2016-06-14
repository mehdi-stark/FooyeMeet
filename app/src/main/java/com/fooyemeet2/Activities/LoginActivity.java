package com.fooyemeet2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fooyemeet2.Activities.Fragments.FragmentInto;
import com.fooyemeet2.Activities.Fragments.FragmentSocialNetwork;
import com.fooyemeet2.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Mehdi on 29/02/2016.
 */

public class LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "X3XLVvC5vqwH6ovptMqx142Xg";
    private static final String TWITTER_SECRET = "6JNh9ip4C2wPhpX4GS5kxeZITEMyzy4cmOHaor7nN5I4caNUCW";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_main);
        FragmentInto fragmentIntro = new FragmentInto();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_login, fragmentIntro).commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentInto fragment = (FragmentInto) getSupportFragmentManager().findFragmentById(R.id.container_login);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }
}