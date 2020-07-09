package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;

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
public interface IntegralContract {

    interface Repository {
        //积分首页
        Observable<BaseEntity<IntegralInfoBean>> integralInfo();

        //1-积分任务列表
        Observable<BaseEntity<List<IntegralTaskBean>>> integralTask();

        //1-积分列表
        Observable<BaseEntity<IntegralDataBean>> integralData(int type, int pageIndex, int pageSize);

        //1-积分购买（签到）
        Observable<BaseEntity<String>> integrlPay(int type, String integrl, String repair_time);


    }


    interface Model {
        void integralInfo();

        void integralTask();

        void integralData(int type, int pageIndex, int pageSize);

        void integrlPay(int type, String integrl, String repair_time);
    }
}
