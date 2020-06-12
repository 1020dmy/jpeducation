package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.contract.ClassInfoContract;
import com.jianpei.jpeducation.repository.ClassInfoRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ClassInfoFModel extends BaseViewModel implements ClassInfoContract.Model {

    ClassInfoRepository classInfoRepository;

    private MutableLiveData<ClassInfoBean> classInfoBean;

    public MutableLiveData<ClassInfoBean> getClassInfoBean() {
        if(classInfoBean==null){
            classInfoBean=new MutableLiveData<>();
        }
        return classInfoBean;
    }

    public ClassInfoFModel() {

        classInfoRepository = new ClassInfoRepository();
    }

    @Override
    public void groupInfo(String groupId, String regimentId) {

        if (TextUtils.isEmpty(groupId)) {
            return;
        }

        classInfoRepository.groupInfo(groupId, regimentId).compose(setThread()).subscribe(new BaseObserver<ClassInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<ClassInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    classInfoBean.setValue(t.getData());
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
