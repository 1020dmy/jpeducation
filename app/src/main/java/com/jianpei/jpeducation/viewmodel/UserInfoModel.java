package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.UserInfoContract;
import com.jianpei.jpeducation.repository.UserInfoRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UserInfoModel extends BaseViewModel implements UserInfoContract.Model {


    private UserInfoRepository userInfoRepository;

    public UserInfoModel() {
        userInfoRepository = new UserInfoRepository();
    }


    private MutableLiveData<UserInfoBean> userInfoBeanLiveData;

    public MutableLiveData<UserInfoBean> getUserInfoBeanLiveData() {
        if (userInfoBeanLiveData == null)
            userInfoBeanLiveData = new MutableLiveData<>();
        return userInfoBeanLiveData;
    }

    @Override
    public void userInfo() {

        userInfoRepository.userInfo().compose(setThread()).subscribe(new BaseObserver<UserInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<UserInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    userInfoBeanLiveData.setValue(t.getData());
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
