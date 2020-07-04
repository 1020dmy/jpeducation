package com.jianpei.jpeducation.repository;

import com.alipay.sdk.app.PayTask;
import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.CouponDataJson;
import com.jianpei.jpeducation.bean.json.CheckPayStatusJson;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.json.OrderPaymentJson;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.contract.OrderConfirmContract;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.utils.PayResult;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderConfirmRepository extends BaseRepository implements OrderConfirmContract.Repository {

    @Override
    public Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type) {
        return RetrofitFactory.getInstance().API().couponData(new CouponDataJson(pageIndex, pageSize, type));
    }

    @Override
    public Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id) {
        return RetrofitFactory.getInstance().API().classGenerateOrder(new ClassGenerateOrderJson(goods_type, group_id, coupon_id, order_id, class_ids, suites_ids, regiment_id, gather_id));
    }

    @Override
    public Observable<BaseEntity<OrderPaymentBean>> orderPayment(String type, String order_id) {
        return RetrofitFactory.getInstance().API().orderPayment(new OrderPaymentJson(type, order_id));
    }

    @Override
    public Observable<BaseEntity<CheckPayStatusBean>> checkPayStatus(String order_id, String pay_type) {
        return RetrofitFactory.getInstance().API().checkPayStatus(new CheckPayStatusJson(order_id, pay_type));
    }

    @Override
    public Observable<String> aliPay(String orderInfo, PayTask payTask) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Map<String, String> result = payTask.payV2(orderInfo, true);
                PayResult payResult = new PayResult(result);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                L.e("=====aliPayResult:===resultStatus:" + resultStatus + ",resultInfo:" + resultInfo);
                emitter.onNext(resultStatus);

            }
        });
    }
}
