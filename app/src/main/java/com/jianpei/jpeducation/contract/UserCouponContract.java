package com.jianpei.jpeducation.contract;

import com.alipay.sdk.app.PayTask;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface UserCouponContract {

    interface Repository {
        Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type, String cat_id, String group_id);


    }


    interface Model {
        void couponData(int pageIndex, int pageSize, int type, String cat_id, String group_id);

    }
}
