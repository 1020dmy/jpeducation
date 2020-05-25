package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.contract.RegisteredContract;
import com.jianpei.jpeducation.repository.RegisteredRepository;

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

        successData.setValue("获取验证码成功！");

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
    }
}
