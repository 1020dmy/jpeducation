package com.jianpei.jpeducation.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class BaseFragment extends Fragment {


    public Context context;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(initLayout(), container, false);
        unbinder= ButterKnife.bind(this,rootView);
        initView(rootView);
        initData(context);
        return rootView;

    }

    protected abstract int initLayout();

    protected abstract void initView(final View view);

    protected abstract void initData(Context mContext);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
