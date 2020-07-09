package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.mclass.ClassDataBean;
import com.jianpei.jpeducation.contract.MyClassContract;
import com.jianpei.jpeducation.repository.MyClassRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MyClassModel extends BaseViewModel implements MyClassContract.Model {


    private MyClassRepository myClassRepository;

    public MyClassModel() {

        myClassRepository = new MyClassRepository();
    }

    /**
     * 我的课程列表
     *
     * @param type
     * @param pageIndex
     * @param pageSize
     */

    private MutableLiveData<ClassDataBean> classDataBeanLiveData;

    public LiveData<ClassDataBean> getClassDataBeanLiveData() {
        if (classDataBeanLiveData == null)
            classDataBeanLiveData = new MediatorLiveData<>();
        return classDataBeanLiveData;
    }

    @Override
    public void classData(int type, int pageIndex, int pageSize) {

        myClassRepository.classData(type, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<ClassDataBean>() {

            @Override
            protected void onSuccees(BaseEntity<ClassDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    classDataBeanLiveData.setValue(t.getData());
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
