package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MainModel extends ViewModel {


    private MutableLiveData<String> liveData;


    public MainModel() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    public void upData(String value) {
        liveData.setValue(value);
    }
}
