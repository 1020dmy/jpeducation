package com.jianpei.jpeducation.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class NTabFragmentAdapter extends FragmentPagerAdapter {


    private Fragment fragments[];
    private String[] titles;

    public NTabFragmentAdapter(@NonNull FragmentManager fm, int behavior, Fragment[] fragments, String[] titles) {
        super(fm, behavior);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
