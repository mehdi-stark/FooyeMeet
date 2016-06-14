package com.fooyemeet2.Activities.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.fooyemeet2.R;

/**
 * Created by Mehdi on 29/04/2016.
 */

public class FragmentProfileTab extends Fragment {

    private View rootView;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tablayout_profile, container, false);

        tab_create();

        return rootView;
    }

    protected void tab_create(){

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager_profile);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs_profile);

        TabCreateProfile viewPagerAdapter = new TabCreateProfile(getFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        final TabLayout.Tab profile = tabLayout.newTab();
        final TabLayout.Tab map = tabLayout.newTab();
        final TabLayout.Tab setting = tabLayout.newTab();

        profile.setIcon(R.drawable.ic_one);
        map.setIcon(R.drawable.ic_two);
        setting.setIcon(R.drawable.ic_three);

        tabLayout.addTab(profile, 0);
        tabLayout.addTab(map, 1);
        tabLayout.addTab(setting, 2);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
