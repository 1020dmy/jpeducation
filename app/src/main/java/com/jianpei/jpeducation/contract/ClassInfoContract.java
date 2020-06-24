package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;

import java.util.List;

import io.reactivex.Observable;

public interface ClassInfoContract {

    interface Repository {

        Observable<BaseEntity<List<GroupClassBean>>> groupClass(String groupId, String regimentId);

        Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id);


    }


    interface Model {
        void groupClass(String groupId, String regimentId);

        void classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, List<String> class_ids, List<String> suites_ids, String regiment_id, String gather_id);

    }
}
