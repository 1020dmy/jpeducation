package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.contract.OrderConfirmContract;
import com.jianpei.jpeducation.repository.OrderConfirmRepository;

import java.util.ArrayList;

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
    public void couponData(String pageIndex, String pageSize, String type) {

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
}
