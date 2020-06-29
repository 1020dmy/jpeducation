package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface OrderConfirmContract {

    interface Repository {
        Observable<BaseEntity<CouponDataBean>> couponData(String pageIndex, String pageSize, String type);

        Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id);


    }


    interface Model {
        void couponData(String pageIndex, String pageSize, String type);


        void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id);


    }
}
