package com.fooyemeet2.Activities.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fooyemeet2.R;

/**
 * Created by Mehdi on 29/04/2016.
 */

public class TabCreateProfile extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;


    public TabCreateProfile(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position){
            case 0:
                frag= new FragmentProfil();
                break;
            case 1:
                frag= new FragmentMap();
                break;
            case 2:
                frag= new FragmentSetting();
        }
        return frag;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

/*    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="Profile";
                break;
            case 1:
                title="Map";
                break;
            case 2:
                title="Setting";
                break;
        }
        return title;
    }*/
}
