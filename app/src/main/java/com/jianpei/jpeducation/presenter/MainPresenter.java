package com.jianpei.jpeducation.presenter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MainPresenter extends ViewModel {
    private MutableLiveData<String> liveData;


    public MainPresenter() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public void upData(String value) {
        liveData.setValue(value);
    }
}
