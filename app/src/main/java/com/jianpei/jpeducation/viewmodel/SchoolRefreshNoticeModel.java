package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.base.BaseViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/24
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class SchoolRefreshNoticeModel extends BaseViewModel {

    private MutableLiveData<String> refreshNoticeLiveData;

    public MutableLiveData<String> getRefreshNoticeLiveData() {
        if (refreshNoticeLiveData == null) {
            refreshNoticeLiveData = new MutableLiveData<>();
        }
        return refreshNoticeLiveData;
    }
}
