package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.BindPhoneContract;
import com.jianpei.jpeducation.repository.BindPhoneRepository;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class BindPhoneModel extends BaseViewModel<String> implements BindPhoneContract.Model {

    private BindPhoneRepository bindPhoneRepository;

    public BindPhoneModel() {

        bindPhoneRepository = new BindPhoneRepository();
    }

    @Override
    public void bindPhone(String uid, String phone, String code) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            errData.setValue("手机号输入有误！");
            return;
        }

        if (TextUtils.isEmpty(code)) {
            errData.setValue("请输入验证码！");
            return;
        }

        if (uid.isEmpty()) {
            errData.setValue("数据获取失败！");
            return;
        }

        bindPhoneRepository.bindPhone(uid,phone,code).compose(setThread()).subscribe(new BaseObserver<UserInfoBean>(){

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

//    @Override
//    public void sendCode(String phone) {
//        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
//            errData.setValue("手机号输入有误！");
//            return;
//        }
//
//        bindPhoneRepository.sendCode(phone).compose(setThread()).subscribe(new BaseObserver<String>() {
//
//            @Override
//            protected void onSuccees(BaseEntity<String> t) throws Exception {
//                if (t.isSuccess()) {
//                    errData.setValue("sjl");
//                } else {
//                    errData.setValue(t.getMsg());
//                }
//
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                if (isNetWorkError) {
//                    errData.setValue("网络异常！");
//                } else {
//                    errData.setValue(e.getMessage());
//                }
//            }
//        });
//
//    }
}
