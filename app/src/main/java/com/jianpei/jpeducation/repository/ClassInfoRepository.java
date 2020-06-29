package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.GroupClassBean;
import com.jianpei.jpeducation.bean.classinfo.ImputedPriceBean;
import com.jianpei.jpeducation.bean.json.ClassGenerateOrderJson;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.bean.json.ImputedPriceJson;
import com.jianpei.jpeducation.bean.order.ClassGenerateOrderBean;
import com.jianpei.jpeducation.contract.ClassInfoContract;
import com.jianpei.jpeducation.utils.L;

import java.util.List;

import io.reactivex.Observable;

public class ClassInfoRepository extends BaseRepository implements ClassInfoContract.Repository {

    @Override
    public Observable<BaseEntity<List<GroupClassBean>>> groupClass(String groupId, String regimentId) {
        return RetrofitFactory.getInstance().API().groupClass(new GroupInfoJson(groupId, regimentId));
    }

    @Override
    public Observable<BaseEntity<ClassGenerateOrderBean>> classGenerateOrder(String goods_type, String group_id, String coupon_id, String order_id, String class_ids, String suites_ids, String regiment_id, String gather_id) {
        return RetrofitFactory.getInstance().API().classGenerateOrder(new ClassGenerateOrderJson(goods_type, group_id, coupon_id, order_id, class_ids, suites_ids, regiment_id, gather_id));
    }

    @Override
    public Observable<BaseEntity<ImputedPriceBean>> imputedPrice(String group_id, String class_ids, String suites_ids, String regiment_id) {
        return RetrofitFactory.getInstance().API().imputedPrice(new ImputedPriceJson(group_id, class_ids, suites_ids, regiment_id));
    }
}
