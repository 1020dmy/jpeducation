package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;


import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.LoginContract;
import com.jianpei.jpeducation.repository.LoginRepository;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/13
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginModel extends BaseViewModel implements LoginContract.Model {

    private LoginRepository loginRepository;

    public LoginModel() {

        loginRepository = new LoginRepository();

    }


    @Override
    public void login(String mobile, String password) {

        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }
        if (password.isEmpty()) {
            errData.setValue("请输入密码！");
            return;
        }
        loginRepository.login(mobile, password).compose(this.setThread()).subscribe(new BaseObserver<UserInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<UserInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    if (t.getData() != null) {
                        SpUtils.putString(SpUtils.ID, t.getData().getId());
                        SpUtils.putString(SpUtils.USERNAME, t.getData().getUser_name());
                        SpUtils.putString(SpUtils.PHONE, t.getData().getPhone());
                        successData.setValue(t.getMsg());
                    } else {
                        errData.setValue("数据获取失败！");
                    }                } else {
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
