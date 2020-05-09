package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jianpei.jpeducation.bean.LoginBean;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/8
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class LoginModel extends ViewModel {

    private MutableLiveData<LoginBean> data;

    public MutableLiveData<LoginBean> Login() {


        return data;

    }


}
