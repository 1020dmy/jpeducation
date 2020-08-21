package com.jianpei.jpeducation.base;


import com.jianpei.jpeducation.utils.L;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public abstract class LazyLoadFragment extends BaseFragment {
    private boolean isFirstLoad=true;


    protected abstract void loadData();

    @Override
    public void onResume() {
        super.onResume();
        L.e("========isFirstLoad="+isFirstLoad);
        if (isFirstLoad) {
            L.e("======loadData==="+isFirstLoad);
            isFirstLoad = false;
            loadData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
