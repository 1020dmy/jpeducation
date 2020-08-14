package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.CouponDataBean;
import com.jianpei.jpeducation.bean.CouponDataJson;
import com.jianpei.jpeducation.contract.UserCouponContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class UserCouponRepository extends BaseRepository implements UserCouponContract.Repository {

    @Override
    public Observable<BaseEntity<CouponDataBean>> couponData(int pageIndex, int pageSize, int type,String cat_id,String group_id) {
        return RetrofitFactory.getInstance().API().couponData(new CouponDataJson(pageIndex, pageSize, type, cat_id, group_id));
    }
}
