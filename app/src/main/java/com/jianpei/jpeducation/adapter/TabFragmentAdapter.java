package com.jianpei.jpeducation.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.List;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TabFragmentAdapter extends FragmentStateAdapter {

    private Fragment[] fragments;


    public TabFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Fragment[] fragments) {
        super(fragmentManager, lifecycle);
        this.fragments = fragments;
    }

    public TabFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragmentList) {
        super(fragmentManager, lifecycle);
        if (fragmentList != null)
            fragments = fragmentList.toArray(new Fragment[fragmentList.size()]);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }

}
