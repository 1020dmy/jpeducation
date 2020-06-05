package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseView;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.UserInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MaterialContract {

    interface Repository {

        Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId);

        Observable<BaseEntity<String>> integrlPay(String integrl);

    }


    interface Model {
        void getDownloadUrl(String fileId);

        void integrlPay(String integrl);


    }
}
