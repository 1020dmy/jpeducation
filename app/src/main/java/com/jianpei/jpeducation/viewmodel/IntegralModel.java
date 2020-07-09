package com.jianpei.jpeducation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.integral.IntegralDataBean;
import com.jianpei.jpeducation.bean.integral.IntegralInfoBean;
import com.jianpei.jpeducation.bean.integral.IntegralTaskBean;
import com.jianpei.jpeducation.contract.IntegralContract;
import com.jianpei.jpeducation.repository.IntegralRepository;

import java.util.List;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/6
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class IntegralModel extends BaseViewModel implements IntegralContract.Model {


    private IntegralRepository integralRepository;


    private MutableLiveData<IntegralInfoBean> infoBeanLiveData;

    private MutableLiveData<List<IntegralTaskBean>> listLiveData;


    public MutableLiveData<IntegralInfoBean> getInfoBeanLiveData() {
        if (infoBeanLiveData == null) {
            infoBeanLiveData = new MutableLiveData<>();
        }
        return infoBeanLiveData;
    }

    public MutableLiveData<List<IntegralTaskBean>> getListLiveData() {
        if (listLiveData == null)
            listLiveData = new MutableLiveData<>();
        return listLiveData;
    }

    public IntegralModel() {
        integralRepository = new IntegralRepository();
    }


    /**
     * 积分首页
     */
    @Override
    public void integralInfo() {
        integralRepository.integralInfo().compose(setThread()).subscribe(new BaseObserver<IntegralInfoBean>() {
            @Override
            protected void onSuccees(BaseEntity<IntegralInfoBean> t) throws Exception {
                if (t.isSuccess()) {
                    infoBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络错误！");
                } else {
                    errData.setValue(e.getMessage());

                }
            }
        });

    }

    /**
     * 积分任务
     */
    @Override
    public void integralTask() {
        integralRepository.integralTask().compose(setThread()).subscribe(new BaseObserver<List<IntegralTaskBean>>() {

            @Override
            protected void onSuccees(BaseEntity<List<IntegralTaskBean>> t) throws Exception {
                if (t.isSuccess()) {
                    listLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络错误！");
                } else {
                    errData.setValue(e.getMessage());

                }
            }
        });

    }


    private MutableLiveData<IntegralDataBean> integralDataLiveData;

    public MutableLiveData<IntegralDataBean> getIntegralDataLiveData() {
        if (integralDataLiveData == null)
            integralDataLiveData = new MutableLiveData<>();
        return integralDataLiveData;
    }

    /**
     * 积分列表
     *
     * @param type
     * @param pageIndex
     * @param pageSize
     */

    @Override
    public void integralData(int type, int pageIndex, int pageSize) {

        integralRepository.integralData(type, pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<IntegralDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<IntegralDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    integralDataLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络错误！");
                } else {
                    errData.setValue(e.getMessage());

                }
            }
        });

    }

    private MutableLiveData<String> integrlPayLiveData;

    public MutableLiveData<String> getIntegrlPayLiveData() {
        if (integrlPayLiveData == null)
            integrlPayLiveData = new MutableLiveData<>();
        return integrlPayLiveData;
    }

    /**
     * 积分支付
     *
     * @param type
     * @param integrl
     * @param repair_time
     */

    @Override
    public void integrlPay(int type, String integrl, String repair_time) {
        integralRepository.integrlPay(type, integrl, repair_time).compose(setThread()).subscribe(new BaseObserver<String>() {

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                if (t.isSuccess()) {
                    integrlPayLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                if (isNetWorkError) {
                    errData.setValue("网络错误！");
                } else {
                    errData.setValue(e.getMessage());

                }
            }
        });

    }
}
