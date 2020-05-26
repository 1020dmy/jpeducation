package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/26
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ForgetPwdContract {


    interface Repository {
        Observable<BaseEntity<String>> sendCode(String phone);

        Observable<BaseEntity<String>> changePwd(String phone, String code, String pwd, String pwdR);

    }


    interface Model {
        void codeLogin(String phone, String code, String pwd, String pwdR);

        void sendCode(String phone);

    }
}
