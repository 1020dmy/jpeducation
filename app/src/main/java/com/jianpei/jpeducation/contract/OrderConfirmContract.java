package com.jianpei.jpeducation.contract;

import com.alipay.sdk.app.PayTask;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type);

        Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id);

        Observable<BaseEntity<OrderPaymentBean>> orderPayment(String type, String order_id);

        Observable<BaseEntity<CheckPayStatusBean>> checkPayStatus(String order_id, String pay_type);

        Observable<String> aliPay(String orderInfo, PayTask payTask);

    }


    interface Model {
        void couponData(int pageIndex, int pageSize, int type);


        void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id);

        void orderPayment(String type, String order_id);

        void checkPayStatus(String order_id, String pay_type);

        void aliPay(String orderInfo, PayTask payTask);
    }
}
