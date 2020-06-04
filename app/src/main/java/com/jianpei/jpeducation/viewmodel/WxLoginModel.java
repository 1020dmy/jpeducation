package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.contract.WxLoginContract;
import com.jianpei.jpeducation.repository.WxLoginRepository;
import com.jianpei.jpeducation.utils.SpUtils;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/5/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class WxLoginModel extends BaseViewModel<String> implements WxLoginContract.Model {


    private WxLoginRepository wxLoginRepository;

    public WxLoginModel() {

        wxLoginRepository = new WxLoginRepository();
    }

    @Override
    public void wxLogin(String refreshToken, String expiration, String screen_name, String access_token, String city, String gender, String openid, String province, String iconurl) {
        if (TextUtils.isEmpty(openid)) {
            errData.setValue("授权失败！");
            return;
        }
        wxLoginRepository.wxLogin(refreshToken, expiration, screen_name, access_token, city, gender, openid, province, iconurl).compose(setThread()).subscribe(new BaseObserver<UserInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<UserInfoBean> t) throws Exception {

                if (t.isSuccess()) {
                    if (t.getData() != null) {
                        SpUtils.putString(SpUtils.ID, t.getData().getId());
                        SpUtils.putString(SpUtils.USERNAME, t.getData().getUser_name());
                        SpUtils.putString(SpUtils.PHONE, t.getData().getPhone());
                        successData.setValue(t.getData().getPhone());
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
}
