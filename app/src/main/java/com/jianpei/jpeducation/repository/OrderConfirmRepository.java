package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.CouponDataJson;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.contract.OrderConfirmContract;

import java.util.ArrayList;

import io.reactivex.Observable;

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
    public Observable<BaseEntity<CouponDataBean>> couponData(String pageIndex, String pageSize, String type) {
        return RetrofitFactory.getInstance().API().couponData(new CouponDataJson(pageIndex, pageSize, type));
    }

    @Override
    public Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id) {
        return RetrofitFactory.getInstance().API().classGenerateOrder(new ClassGenerateOrderJson(goods_type, group_id, coupon_id, order_id, class_ids, suites_ids, regiment_id, gather_id));
    }

}
