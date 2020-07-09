package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.IntegrlPayJson;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;
import com.jianpei.jpeducation.bean.json.CarInfoJson;
import com.jianpei.jpeducation.bean.json.IntegralDataJson;
import com.jianpei.jpeducation.contract.IntegralContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralRepository extends BaseRepository implements IntegralContract.Repository {


    @Override
    public Observable<BaseEntity<IntegralInfoBean>> integralInfo() {
        return RetrofitFactory.getInstance().API().integralInfo(new CarInfoJson());
    }

    @Override
    public Observable<BaseEntity<List<IntegralTaskBean>>> integralTask() {
        return RetrofitFactory.getInstance().API().integralTask(new CarInfoJson());
    }

    @Override
    public Observable<BaseEntity<IntegralDataBean>> integralData(int type, int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().integralData(new IntegralDataJson(type, pageIndex, pageSize));
    }

    @Override
    public Observable<BaseEntity<String>> integrlPay(int type, String integrl, String repair_time) {
        return RetrofitFactory.getInstance().API().integrlPay(new IntegrlPayJson(type, integrl, repair_time));
    }
}
