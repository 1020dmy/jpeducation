package com.jianpei.jpeducation.viewmodel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.contract.LoginContract;
import com.jianpei.jpeducation.repository.LoginRepository;

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

        loginRepository=new LoginRepository();

    }
    public MutableLiveData<String> getScuucessData() {
        if (successData == null) {
            successData = new MutableLiveData<>();
        }
        return successData;
    }

    public MutableLiveData<String> getErrData() {
        if (errData == null) {
            errData = new MutableLiveData<>();
        }
        return errData;
    }

    @Override
    public void login(String mobile, String password) {

        if (mobile.isEmpty()) {
            errData.setValue("请输入手机号！");
            return;
        }
        if (password.isEmpty()) {
            errData.setValue("请输入密码！");
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                successData.setValue("登陆成功");

            }
        },2000);
//        model.login(mobile, password).compose(this.setThread()).subscribe(new BaseObserver<LoginBean>() {
//
//            @Override
//            protected void onSuccees(BaseEntity<LoginBean> t) throws Exception {
//                if (t.isSuccess()) {//
//                    SpUtils.putString(SpUtils.USERNAME, t.getData().getName());
//                    SpUtils.putString(SpUtils.TOKEN, t.getData().getToken());
//                    successData.setValue(t.getMsg());
//                } else {
//                    errData.setValue(t.getMsg());
//                }
//            }
//
//            @Override
//            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
//                errData.setValue(e.getMessage());
//            }
//        });
    }
}
