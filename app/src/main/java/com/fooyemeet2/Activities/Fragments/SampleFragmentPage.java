package com.fooyemeet2.Activities.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.fooyemeet2.R;

/**
 * Created by Mehdi on 17/04/2016.
 */
public class SampleFragmentPage extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 2;


    public SampleFragmentPage(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag= new FragmentLogin();
                break;
            case 1:
                frag= new FragmentRegister();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Sign in";
                break;
            case 1:
                title="Sign up";
                break;
        }
        return title;
    }
}
