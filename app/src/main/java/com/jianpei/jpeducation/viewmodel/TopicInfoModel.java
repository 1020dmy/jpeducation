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
public class TopicInfoModel extends BaseViewModel {


    private MutableLiveData<String> viewNamLiveData;


    public MutableLiveData<String> getViewNamLiveData() {
        if (viewNamLiveData == null)
            viewNamLiveData = new MutableLiveData<>();
        return viewNamLiveData;
    }
}
