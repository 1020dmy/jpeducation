package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.contract.MaterialContract;
import com.jianpei.jpeducation.repository.MaterialRepository;
import com.jianpei.jpeducation.utils.L;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialModel extends BaseViewModel implements MaterialContract.Model {


    private MutableLiveData<DownloadBean> downloadBeanMutableLiveData;

    private MutableLiveData<String> integrlPayLiveData;

    private MaterialRepository materialRepository;


    public MutableLiveData<DownloadBean> getDownloadBeanMutableLiveData() {
        if (downloadBeanMutableLiveData == null) {
            downloadBeanMutableLiveData = new MutableLiveData<>();
        }
        return downloadBeanMutableLiveData;
    }

    public MutableLiveData<String> getIntegrlPayLiveData() {
        if (integrlPayLiveData == null) {
            integrlPayLiveData = new MutableLiveData<>();
        }
        return integrlPayLiveData;
    }

    public MaterialModel() {
        materialRepository = new MaterialRepository();
    }

    @Override
    public void getDownloadUrl(String fileId) {
        if (TextUtils.isEmpty(fileId)) {
            errData.setValue("文件ID不能为空");
            return;
        }

        materialRepository.getDownloadUrl(fileId).compose(setThread()).subscribe(new BaseObserver<DownloadBean>() {

            @Override
            protected void onSuccees(BaseEntity<DownloadBean> t) throws Exception {
                if (t.isSuccess()) {
                    if (downloadBeanMutableLiveData == null) {
                        downloadBeanMutableLiveData = new MutableLiveData<>();
                    }
                    downloadBeanMutableLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }


            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                L.e("===getDownloadUrl==onFailure=="+e.getMessage());

                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    L.e("========="+e.getMessage());
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    @Override
    public void integrlPay(String integrl) {

        materialRepository.integrlPay(integrl).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    integrlPayLiveData.setValue(t.getMsg());
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
