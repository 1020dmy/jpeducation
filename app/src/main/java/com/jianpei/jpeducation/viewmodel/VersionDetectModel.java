package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.VersionDetectBean;
import com.jianpei.jpeducation.contract.VersionDetectContract;
import com.jianpei.jpeducation.repository.VersionDetectRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/10
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class VersionDetectModel extends BaseViewModel implements VersionDetectContract.Model {


    private VersionDetectRepository repository;

    public VersionDetectModel() {
        repository = new VersionDetectRepository();
    }

    /**
     * 版本检测
     */
    private MutableLiveData<VersionDetectBean> versionDetectLiveData;

    public MutableLiveData<VersionDetectBean> getVersionDetectLiveData() {
        if (versionDetectLiveData == null)
            versionDetectLiveData = new MutableLiveData<>();
        return versionDetectLiveData;
    }

    @Override
    public void versionDetect() {
        repository.versionDetect().compose(setThread()).subscribe(new BaseObserver<VersionDetectBean>() {
            @Override
            protected void onSuccees(BaseEntity<VersionDetectBean> t) throws Exception {
                if (t.isSuccess()) {
                    versionDetectLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }
}
