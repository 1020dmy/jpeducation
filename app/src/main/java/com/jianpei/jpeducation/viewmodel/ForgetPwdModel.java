package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.contract.ForgetPwdContract;
import com.jianpei.jpeducation.repository.ForgetPwdRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ForgetPwdModel extends BaseViewModel implements ForgetPwdContract.Model {


    protected ForgetPwdRepository forgetPwdRepository;

    public ForgetPwdModel() {
        forgetPwdRepository = new ForgetPwdRepository();
    }

    @Override
    public void codeLogin(String phone, String code, String pwd, String pwdR) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            errData.setValue("请输入验证码！");
            return;
        }

        if (pwd.isEmpty()) {
            errData.setValue("请输入密码！");
            return;
        }
        if (!pwd.equals(pwdR)) {
            errData.setValue("两次密码输入不一致！");
            return;
        }

        forgetPwdRepository.changePwd(phone,code,pwd,pwdR).compose(setThread()).subscribe(new BaseObserver<String>(){

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {

                if(t.isSuccess()){
                    successData.setValue(t.getMsg());
                }else{
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

    @Override
    public void sendCode(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }

        forgetPwdRepository.sendCode(phone).compose(setThread()).subscribe(new BaseObserver<String>() {

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
}
