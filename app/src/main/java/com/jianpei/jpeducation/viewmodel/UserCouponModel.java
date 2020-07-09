package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.contract.UserCouponContract;
import com.jianpei.jpeducation.repository.UserCouponRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UserCouponModel extends BaseViewModel implements UserCouponContract.Model {

    private UserCouponRepository userCouponRepository;

    private MutableLiveData<CouponDataBean> couponDataBeanLiveData;


    public MutableLiveData<CouponDataBean> getCouponDataBeanLiveData() {
        if (couponDataBeanLiveData == null)
            couponDataBeanLiveData = new MutableLiveData<>();
        return couponDataBeanLiveData;
    }

    public UserCouponModel() {
        userCouponRepository = new UserCouponRepository();
    }

    @Override
    public void couponData(int pageIndex, int pageSize, int type) {

        userCouponRepository.couponData(pageIndex, pageSize, type).compose(setThread()).subscribe(new BaseObserver<CouponDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<CouponDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    couponDataBeanLiveData.setValue(t.getData());
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
