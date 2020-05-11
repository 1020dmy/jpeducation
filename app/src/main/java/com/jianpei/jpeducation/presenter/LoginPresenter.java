package com.jianpei.jpeducation.presenter;

import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.LoginBean;
import com.jianpei.jpeducation.contract.LoginContract;
import com.jianpei.jpeducation.model.LoginModel;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/11
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginPresenter extends BaseViewModel<String> implements LoginContract.Presenter {


    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
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