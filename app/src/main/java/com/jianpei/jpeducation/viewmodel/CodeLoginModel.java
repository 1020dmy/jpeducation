package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.CodeLoginContract;
import com.jianpei.jpeducation.repository.CodeLoginRepository;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class CodeLoginModel extends BaseViewModel<String> implements CodeLoginContract.Model {


    protected CodeLoginRepository codeLoginRepository;


    public CodeLoginModel() {
        codeLoginRepository = new CodeLoginRepository();
    }

    @Override
    public void codeLogin(String phone, String code) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            errData.setValue("请输入验证码！");
            return;
        }
        codeLoginRepository.codeLogin(phone, code).compose(setThread()).subscribe(new BaseObserver<UserInfoBean>() {

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

    @Override
    public void sendCode(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }
        codeLoginRepository.sendCode(phone).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
//                    successData.setValue(t.getMsg());
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
