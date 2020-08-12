package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.contract.ShoppingCartContract;
import com.jianpei.jpeducation.repository.ShoppingCartRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ShoppingCartModel extends BaseViewModel implements ShoppingCartContract.Model {

    private ShoppingCartRepository shoppingCartRepository;


    private MutableLiveData<MIneOrderInfoBean> carInfoBeanLiveData;

    private MutableLiveData<CouponDataBean> couponDataBeanLiveData;

    private MutableLiveData<String> removeCarLiveData;


    public MutableLiveData<String> getRemoveCarLiveData() {
        if (removeCarLiveData == null)
            removeCarLiveData = new MutableLiveData<>();
        return removeCarLiveData;
    }

    public MutableLiveData<MIneOrderInfoBean> getCarInfoBeanLiveData() {
        if (carInfoBeanLiveData == null)
            carInfoBeanLiveData = new MutableLiveData<>();
        return carInfoBeanLiveData;
    }

    public MutableLiveData<CouponDataBean> getCouponDataBeanLiveData() {

        if (couponDataBeanLiveData == null)
            couponDataBeanLiveData = new MutableLiveData<>();
        return couponDataBeanLiveData;
    }

    public ShoppingCartModel() {
        shoppingCartRepository = new ShoppingCartRepository();
    }

    /**
     * 删除购物车
     *
     * @param car_id
     * @param order_id
     */
    @Override
    public void removeCar(String car_id, String order_id) {
        if (TextUtils.isEmpty(car_id) || TextUtils.isEmpty(order_id))
            return;
        shoppingCartRepository.removeCar(car_id, order_id).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    removeCarLiveData.setValue("删除完成！");
                } else
                    errData.setValue(t.getMsg());
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

    /**
     * 购物车详情
     */

    @Override
    public void carInfo() {

        shoppingCartRepository.carInfo().compose(setThread()).subscribe(new BaseObserver<MIneOrderInfoBean>() {

            @Override
            protected void onSuccees(BaseEntity<MIneOrderInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    carInfoBeanLiveData.setValue(t.getData());
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

    /**
     * 优惠券列表
     *
     * @param pageIndex
     * @param pageSize
     * @param type
     */

    @Override
    public void couponData(int pageIndex, int pageSize, int type) {

        shoppingCartRepository.couponData(pageIndex, pageSize, type).compose(setThread()).subscribe(new BaseObserver<CouponDataBean>() {

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
