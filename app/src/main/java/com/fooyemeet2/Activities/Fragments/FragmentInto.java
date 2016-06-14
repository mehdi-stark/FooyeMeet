package com.fooyemeet2.Activities.Fragments;

import android.content.Intent;
import android.net.Uri;
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
 * Created by Mehdi on 24/04/2016.
 */

public class FragmentInto extends Fragment {

    private static final int SELECT_PICTURE = 1;
    private static final int RESULT_OK = -1;
    private View rootView;
    private ImageView mImageView;
    private ViewPager pager;
    private ImageButton mImageButton;
    private String selectedImagePath;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tablayout_container, container, false);

        mImageView = (ImageView) rootView.findViewById(R.id.imageView);
        mImageButton = (ImageButton) rootView.findViewById(R.id.imageButton);

        tab_create2();
        return rootView;
    }

    protected void tab_create2() {

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        pager = (ViewPager) rootView.findViewById(R.id.viewpager);

        SampleFragmentPage pagerAdapter = new SampleFragmentPage(getFragmentManager());
        pager.setAdapter(pagerAdapter);

        final TabLayout.Tab signIn = tabLayout.newTab();
        final TabLayout.Tab signUp = tabLayout.newTab();

        signIn.setText("SIGN IN");
        signUp.setText("SIGN UP");

        tabLayout.addTab(signIn, 0);
        tabLayout.addTab(signUp, 1);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    mImageButton.setVisibility(View.VISIBLE);
                    mImageView.setVisibility(View.INVISIBLE);
                } else {
                    mImageButton.setVisibility(View.INVISIBLE);
                    mImageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = selectedImageUri.getPath();
               // mImageButton.setImageResource();
            }
        }
    }
    /*
    protected void tab_create() {

        pager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);

        //object of PagerAdapter passing fragment manager object as a parameter of constructor of PagerAdapter class.
        PagerAdapter adapter = new SampleFragmentPage(getChildFragmentManager(), getContext());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //Setting tabs from adpater
        tabLayout.setTabsFromPagerAdapter(adapter);
    }*/
}
