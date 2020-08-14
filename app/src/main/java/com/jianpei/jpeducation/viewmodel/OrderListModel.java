package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.contract.OrderListContract;
import com.jianpei.jpeducation.repository.OrderListRepository;
import com.jianpei.jpeducation.utils.L;


/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderListModel extends BaseViewModel implements OrderListContract.Model {


    private OrderListRepository orderListRepository;

    private MutableLiveData<OrderListBean> orderDataBeansLiveData;

    private MutableLiveData<String> cancleOrderLiveData;

    //购买课程下单/计算价格结果（生成订单）

    private MutableLiveData<MIneOrderInfoBean> classGenerateOrderBeanLiveData;

    public MutableLiveData<OrderListBean> getOrderDataBeansLiveData() {
        if (orderDataBeansLiveData == null)
            orderDataBeansLiveData = new MutableLiveData<>();
        return orderDataBeansLiveData;
    }

    public MutableLiveData<MIneOrderInfoBean> getClassGenerateOrderBeanLiveData() {

        if (classGenerateOrderBeanLiveData == null) {
            classGenerateOrderBeanLiveData = new MutableLiveData<>();
        }
        return classGenerateOrderBeanLiveData;
    }

    public MutableLiveData<String> getCancleOrderLiveData() {
        if (cancleOrderLiveData == null)
            cancleOrderLiveData = new MutableLiveData<>();
        return cancleOrderLiveData;
    }

    public OrderListModel() {
        orderListRepository = new OrderListRepository();
    }

    @Override
    public void orderData(int type, int pageIndex, int pageSize) {


        orderListRepository.orderData(type, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<OrderListBean>() {

            @Override
            protected void onSuccees(BaseEntity<OrderListBean> t) throws Exception {

                if (t.isSuccess()) {
                    if (t.getData() != null) {
                        orderDataBeansLiveData.setValue(t.getData());
                    } else {
                        orderDataBeansLiveData.setValue(null);

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

    @Override
    public void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id) {
        orderListRepository.classGenerateOrder(goods_type, group_id, coupon_id, order_id, "", "", "", "").compose(setThread()).subscribe(new BaseObserver<MIneOrderInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<MIneOrderInfoBean> t) throws Exception {
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

    /**
     * 取消订单
     *
     * @param order_id
     */
    @Override
    public void cancelOrder(String order_id) {
        if (TextUtils.isEmpty(order_id))
            return;

        orderListRepository.cancelOrder(order_id).compose(setThread()).subscribe(new BaseObserver<String>() {
            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    cancleOrderLiveData.setValue(t.getMsg());
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
     * 订单详情
     *
     * @param order_id
     */

    private MutableLiveData<MIneOrderInfoBean> orderInfoBeanMutableLiveData;

    public MutableLiveData<MIneOrderInfoBean> getOrderInfoBeanMutableLiveData() {
        if (orderInfoBeanMutableLiveData == null)
            orderInfoBeanMutableLiveData = new MutableLiveData<>();
        return orderInfoBeanMutableLiveData;
    }

    @Override
    public void orderInfo(String order_id) {
        if (TextUtils.isEmpty(order_id)) {
            return;
        }
        orderListRepository.orderInfo(order_id).compose(setThread()).subscribe(new BaseObserver<MIneOrderInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<MIneOrderInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    orderInfoBeanMutableLiveData.setValue(t.getData());
                } else {
                    L.e("======onFailure:");
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                L.e("======onFailure===" + e.getMessage());
                if (isNetWorkError) {
                    errData.setValue("网络异常！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

//    /**
//     * 通知三个列表去更新
//     */
//    private MutableLiveData<String> noticeChangeLiveData;
//
//    public MutableLiveData<String> getNoticeChangeLiveData() {
//        if (noticeChangeLiveData == null)
//            noticeChangeLiveData = new MutableLiveData<>();
//        return noticeChangeLiveData;
//    }
}
