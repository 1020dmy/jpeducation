package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.contract.MainContract;
import com.jianpei.jpeducation.repository.MainRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MainModel extends BaseViewModel implements MainContract.Model {


    private MutableLiveData<String> liveDataCatId;//catid更新


    private MainRepository mainRepository;

    public MainModel() {
        mainRepository = new MainRepository();
    }

    public MutableLiveData<String> getCatId() {
        if (liveDataCatId == null) {
            liveDataCatId = new MutableLiveData<String>();
        }
        return liveDataCatId;
    }

    public void upData(String value) {
        if (liveDataCatId == null) {
            liveDataCatId = new MutableLiveData<String>();
        }
        liveDataCatId.setValue(value);

    }

    @Override
    public void getHomeData(String catId) {
        if (TextUtils.isEmpty(catId)) {
            return;
        }
        mainRepository.getHomeData(catId).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

            }
        });

    }
}
