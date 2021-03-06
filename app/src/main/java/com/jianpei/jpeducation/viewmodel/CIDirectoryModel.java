package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;
import com.jianpei.jpeducation.contract.CIDirectoryContract;
import com.jianpei.jpeducation.repository.CIDirectoryRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CIDirectoryModel extends BaseViewModel implements CIDirectoryContract.Model {


    private CIDirectoryRepository ciDirectoryRepository;

    private MutableLiveData<List<DirectoryProfessionBean>> mutableLiveData;

    private MutableLiveData<List<ViodBean>> viodListBeansLiveData;


    public MutableLiveData<List<ViodBean>> getViodListBeansLiveData() {
        if(viodListBeansLiveData==null)
            viodListBeansLiveData=new MutableLiveData<>();
        return viodListBeansLiveData;
    }

    public MutableLiveData<List<DirectoryProfessionBean>> getMutableLiveData() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public CIDirectoryModel() {
        ciDirectoryRepository = new CIDirectoryRepository();
    }

    /**
     * 1-班级课程目录
     * @param groupId
     */

    @Override
    public void classDirectory(String groupId) {
        if (TextUtils.isEmpty(groupId))
            return;

        ciDirectoryRepository.classDirectory(groupId).compose(setThread()).subscribe(new BaseObserver<List<DirectoryProfessionBean>>() {

            @Override
            protected void onSuccees(BaseEntity<List<DirectoryProfessionBean>> t) throws Exception {
                if (t.isSuccess()) {
                    mutableLiveData.setValue(t.getData());

                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取章节列表
     * @param class_id
     * @param chapter_id
     */

    @Override
    public void viodList(String class_id, String chapter_id) {

        ciDirectoryRepository.viodList(class_id,chapter_id,"1").compose(setThread()).subscribe(new BaseObserver<List<ViodBean>>(){

            @Override
            protected void onSuccees(BaseEntity<List<ViodBean>> t) throws Exception {
                if (t.isSuccess()) {
                    viodListBeansLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }
}
