package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.json.OrderInfoJson;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.bean.order.MIneOrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderDataBean;
import com.jianpei.jpeducation.bean.order.OrderInfoBean;
import com.jianpei.jpeducation.bean.order.OrderListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/2
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface OrderListContract {


    interface Repository {
        Observable<BaseEntity<OrderListBean>> orderData(int type, int pageIndex, int pageSize);


//        Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id);
        Observable<BaseEntity<MIneOrderInfoBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id);


        Observable<BaseEntity<String>> cancelOrder(String order_id);

        /**
         * 订单详情
         *
         * @param order_id
         * @return
         */

        Observable<BaseEntity<MIneOrderInfoBean>> orderInfo(String order_id);


    }


    interface Model {
        void orderData(int type, int pageIndex, int pageSize);

        void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id);

        void cancelOrder(String order_id);

        void orderInfo(String order_id);

    }


}
