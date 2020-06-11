package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoModel extends ViewModel {

    private MutableLiveData<Integer> tabViewStatus;


    public MutableLiveData<Integer> getTabViewStatus() {
        if (tabViewStatus == null) {
            tabViewStatus = new MutableLiveData<>();
        }
        return tabViewStatus;
    }

    public void tabViewStatusChange(int isShow) {
        tabViewStatus.setValue(isShow);
    }
}
