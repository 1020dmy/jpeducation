package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class DataNoticeChangeModel extends ViewModel {


    private MutableLiveData<String> noticeChangeLiveData;


    public MutableLiveData<String> getNoticeChangeLiveData() {
        if (noticeChangeLiveData == null) {
            noticeChangeLiveData = new MutableLiveData<>();
        }
        return noticeChangeLiveData;
    }
}
