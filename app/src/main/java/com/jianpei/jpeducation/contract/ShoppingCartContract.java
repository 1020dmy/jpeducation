package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.shop.CarInfoBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/1
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ShoppingCartContract {
    interface Repository {

        Observable<BaseEntity<String>> removeCar(String car_id, String order_id);

        Observable<BaseEntity<MIneOrderInfoBean>> carInfo();

//        Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type);

    }


    interface Model {
        void removeCar(String car_id, String order_id);

        void carInfo();

//        void couponData(int pageIndex, int pageSize, int type);


    }
}
