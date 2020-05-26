package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.RegisteredContract;
import com.jianpei.jpeducation.repository.RegisteredRepository;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class RegisteredModel extends BaseViewModel<String> implements RegisteredContract.Model {


    private RegisteredRepository registeredRepository;

    public RegisteredModel() {
        registeredRepository = new RegisteredRepository();
    }

    @Override
    public void sendCode(String mobile) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            errData.setValue("手机号码格式错误！");
            return;
        }
        registeredRepository.sendCode(mobile).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

                if (t.isSuccess()) {
                    errData.setValue("sjl");
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

    @Override
    public void register(String mobile, String code, String password, String passwordR) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            errData.setValue("手机号码格式错误！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            errData.setValue("请输入验证码！");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            errData.setValue("请输入密码！");
            return;
        }
        if (!password.equals(passwordR)) {
            errData.setValue("两次密码输入不一致！");
            return;
        }

        registeredRepository.register(mobile, code, password, passwordR).compose(setThread()).subscribe(new BaseObserver<UserInfoBean>() {

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
                    }

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
