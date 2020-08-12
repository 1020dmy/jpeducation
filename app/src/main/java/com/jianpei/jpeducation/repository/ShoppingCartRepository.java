package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.CouponDataJson;
import com.jianpei.jpeducation.bean.json.CarInfoJson;
import com.jianpei.jpeducation.bean.json.RemoveCarJson;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.shop.CarInfoBean;
import com.jianpei.jpeducation.contract.ShoppingCartContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class ShoppingCartRepository extends BaseRepository implements ShoppingCartContract.Repository {

    @Override
    public Observable<BaseEntity<String>> removeCar(String car_id, String order_id) {
        return RetrofitFactory.getInstance().API().removeCar(new RemoveCarJson(car_id, order_id));
    }

    @Override
    public Observable<BaseEntity<MIneOrderInfoBean>> carInfo() {
        return RetrofitFactory.getInstance().API().carInfo(new CarInfoJson());
    }
    @Override
    public Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type) {
        return RetrofitFactory.getInstance().API().couponData(new CouponDataJson(pageIndex, pageSize, type));
    }

}
