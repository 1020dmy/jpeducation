package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/27
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface UserInfoContract {
    interface Repository {
        Observable<BaseEntity<UserInfoBean>> userInfo();

        Observable<BaseEntity<UserInfoBean>> editUser(String avatar, String user_name, String sex, String birthday);
    }

    interface Model {

        void userInfo();

        void editUser(String avatar, String user_name, String sex, String birthday);

    }


}
