package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.bean.LauncherBean;
import com.jianpei.jpeducation.contract.LauncherContract;
import com.jianpei.jpeducation.repository.LauncherRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/22
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LauncherModel extends BaseViewModel<LauncherBean> implements LauncherContract.Model {


    protected LauncherRepository launcherRepository;

//    public MutableLiveData<LauncherBean> getScuucessData() {
//        if (successData == null) {
//            successData = new MutableLiveData<>();
//        }
//        return successData;
//    }
//
//    public MutableLiveData<String> getErrData() {
//        if (errData == null) {
//            errData = new MutableLiveData<>();
//        }
//        return errData;
//    }


    public LauncherModel() {
        launcherRepository = new LauncherRepository();
//        successData = new MutableLiveData();
//        errData = new MutableLiveData<>();


    }

    @Override
    public void appSet() {


        launcherRepository.appSet().compose(setThread()).subscribe(new BaseObserver<LauncherBean>() {


            @Override
            protected void onSuccees(BaseEntity<LauncherBean> t) throws Exception {
                if (t.isSuccess()) {
                    successData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络错误！");
                } else {
                    errData.setValue(e.getMessage());

                }

            }
        });
    }


}
