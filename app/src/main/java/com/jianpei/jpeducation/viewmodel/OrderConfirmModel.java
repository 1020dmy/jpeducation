package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.alipay.sdk.app.PayTask;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.CheckPayStatusBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.OrderPaymentBean;
import com.jianpei.jpeducation.contract.OrderConfirmContract;
import com.jianpei.jpeducation.repository.OrderConfirmRepository;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/28
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderConfirmModel extends BaseViewModel implements OrderConfirmContract.Model {


    private MutableLiveData<CouponDataBean> couponDataBeanLiveData;

    //购买课程下单/计算价格结果（生成订单）

    private MutableLiveData<ClassGenerateOrderBean> classGenerateOrderBeanLiveData;
    //1-订单发起支付
    private MutableLiveData<OrderPaymentBean> orderPaymentBeanLiveData;
    //    支付状态查询
    private MutableLiveData<CheckPayStatusBean> checkPayStatusBeanLiveData;
    //支付宝支付
    private MutableLiveData<String> aliPayLiveData;

    public MutableLiveData<String> getAliPayLiveData() {
        if (aliPayLiveData == null)
            aliPayLiveData = new MutableLiveData<>();
        return aliPayLiveData;
    }

    public MutableLiveData<CheckPayStatusBean> getCheckPayStatusBeanLiveData() {
        if (checkPayStatusBeanLiveData == null)
            checkPayStatusBeanLiveData = new MutableLiveData<>();
        return checkPayStatusBeanLiveData;
    }

    public MutableLiveData<OrderPaymentBean> getOrderPaymentBeanLiveData() {
        if (orderPaymentBeanLiveData == null)
            orderPaymentBeanLiveData = new MutableLiveData<>();
        return orderPaymentBeanLiveData;
    }

    public MutableLiveData<ClassGenerateOrderBean> getClassGenerateOrderBeanLiveData() {

        if (classGenerateOrderBeanLiveData == null) {
            classGenerateOrderBeanLiveData = new MutableLiveData<>();
        }
        return classGenerateOrderBeanLiveData;
    }

    private OrderConfirmRepository orderConfirmRepository;

    public OrderConfirmModel() {

        orderConfirmRepository = new OrderConfirmRepository();
    }

    public MutableLiveData<CouponDataBean> getCouponDataBeanLiveData() {

        if (couponDataBeanLiveData == null)
            couponDataBeanLiveData = new MutableLiveData<>();
        return couponDataBeanLiveData;
    }

    @Override
    public void couponData(int pageIndex, int pageSize, int type) {

        orderConfirmRepository.couponData(pageIndex, pageSize, type).compose(setThread()).subscribe(new BaseObserver<CouponDataBean>() {

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

    @Override
    public void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id) {
        orderConfirmRepository.classGenerateOrder(goods_type, group_id, coupon_id, order_id, "", "", "", "").compose(setThread()).subscribe(new BaseObserver<ClassGenerateOrderBean>() {

            @Override
            protected void onSuccees(BaseEntity<ClassGenerateOrderBean> t) throws Exception {
                if (t.isSuccess()) {
                    classGenerateOrderBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    @Override
    public void orderPayment(String type, String order_id) {
        if (TextUtils.isEmpty(order_id))
            return;
        orderConfirmRepository.orderPayment(type, order_id).compose(setThread()).subscribe(new BaseObserver<OrderPaymentBean>() {

            @Override
            protected void onSuccees(BaseEntity<OrderPaymentBean> t) throws Exception {
                if (t.isSuccess()) {

                    orderPaymentBeanLiveData.setValue(t.getData());

                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 支付状态查询
     *
     * @param order_id
     * @param pay_type
     */

    @Override
    public void checkPayStatus(String order_id, String pay_type) {
        if (TextUtils.isEmpty(order_id))
            return;

        orderConfirmRepository.checkPayStatus(order_id, pay_type).compose(setThread()).subscribe(new BaseObserver<CheckPayStatusBean>() {

            @Override
            protected void onSuccees(BaseEntity<CheckPayStatusBean> t) throws Exception {
                if (t.isSuccess()) {
                    checkPayStatusBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 支付宝支付
     *
     * @param orderInfo
     * @param payTask
     */

    @Override
    public void aliPay(String orderInfo, PayTask payTask) {
        if (TextUtils.isEmpty(orderInfo)) {
            errData.setValue("订单信息获取失败！");
            return;
        }
        orderConfirmRepository.aliPay(orderInfo, payTask).compose(setThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                aliPayLiveData.setValue(s);
            }

            @Override
            public void onError(Throwable e) {
                errData.setValue(e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
