package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.CancelOrderJson;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.json.OrderDataJson;
import com.jianpei.jpeducation.bean.json.OrderInfoJson;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;
import com.jianpei.jpeducation.contract.OrderListContract;


import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class OrderListRepository extends BaseRepository implements OrderListContract.Repository {


    @Override
    public Observable<BaseEntity<OrderListBean>> orderData(int type, int pageIndex, int pageSize) {

        return RetrofitFactory.getInstance().API().orderData(new OrderDataJson(type, pageIndex, pageSize));
    }

    @Override
    public Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id) {
        return RetrofitFactory.getInstance().API().classGenerateOrder(new ClassGenerateOrderJson(goods_type, group_id, coupon_id, order_id, class_ids, suites_ids, regiment_id, gather_id));
    }

    @Override
    public Observable<BaseEntity<String>> cancelOrder(String order_id) {
        return RetrofitFactory.getInstance().API().cancelOrder(new CancelOrderJson(order_id));
    }

    @Override
    public Observable<BaseEntity<MIneOrderInfoBean>> orderInfo(String order_id) {
        return RetrofitFactory.getInstance().API().orderInfo(new OrderInfoJson(order_id));
    }
}
