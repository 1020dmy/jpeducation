package com.jianpei.jpeducation.viewmodel;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.api.base.BaseObserver;
import com.jianpei.jpeducation.base.BaseViewModel;
import com.jianpei.jpeducation.bean.gold.CashWithdrawalBean;
import com.jianpei.jpeducation.bean.gold.VirtualCurrencyListBean;
import com.jianpei.jpeducation.bean.gold.WithdrawalDataBean;
import com.jianpei.jpeducation.contract.GoldContract;
import com.jianpei.jpeducation.repository.GoldRepository;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GoldModel extends BaseViewModel implements GoldContract.Model {

    private GoldRepository goldRepository;

    public GoldModel() {
        goldRepository = new GoldRepository();
    }


    /**
     * 金币列表
     *
     * @param pageIndex
     * @param pageSize
     */
    private MutableLiveData<VirtualCurrencyListBean> virtualCurrencyListBeanLiveData;

    public MutableLiveData<VirtualCurrencyListBean> getVirtualCurrencyListBeanLiveData() {
        if (virtualCurrencyListBeanLiveData == null)
            virtualCurrencyListBeanLiveData = new MutableLiveData<>();
        return virtualCurrencyListBeanLiveData;
    }

    @Override
    public void virtualCurrencyList(int pageIndex, int pageSize) {

        goldRepository.virtualCurrencyList(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<VirtualCurrencyListBean>() {
            @Override
            protected void onSuccees(BaseEntity<VirtualCurrencyListBean> t) throws Exception {

                if (t.isSuccess()) {
                    virtualCurrencyListBeanLiveData.setValue(t.getData());

                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });
    }

    /**
     * 提现列表
     *
     * @param pageIndex
     * @param pageSize
     */

    private MutableLiveData<WithdrawalDataBean> withdrawalDataBeanLiveData;

    public MutableLiveData<WithdrawalDataBean> getWithdrawalDataBeanLiveData() {
        if (withdrawalDataBeanLiveData == null)
            withdrawalDataBeanLiveData = new MutableLiveData<>();
        return withdrawalDataBeanLiveData;
    }

    @Override
    public void withdrawalData(int pageIndex, int pageSize) {
        goldRepository.withdrawalData(pageIndex, pageSize).compose(setThread()).subscribe(new BaseObserver<WithdrawalDataBean>() {
            @Override
            protected void onSuccees(BaseEntity<WithdrawalDataBean> t) throws Exception {
                if (t.isSuccess()) {
                    withdrawalDataBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }

    /**
     * 申请体现
     *
     * @param bank_card
     * @param bank_user_name
     * @param virtual_currency
     */
    private MutableLiveData<CashWithdrawalBean> cashWithdrawalBeanLiveData;

    public MutableLiveData<CashWithdrawalBean> getCashWithdrawalBeanLiveData() {
        if (cashWithdrawalBeanLiveData == null) {
            cashWithdrawalBeanLiveData = new MutableLiveData<>();
        }
        return cashWithdrawalBeanLiveData;
    }

    @Override
    public void cashWithdrawal(String bank_card, String bank_user_name, String virtual_currency) {
        if (TextUtils.isEmpty(bank_card)) {
            errData.setValue("请输入银行卡号");
            return;
        }
        if (TextUtils.isEmpty(bank_user_name)) {
            errData.setValue("请输入姓名");
            return;
        }
        goldRepository.cashWithdrawal(bank_card, bank_user_name, virtual_currency).compose(setThread()).subscribe(new BaseObserver<CashWithdrawalBean>() {

            @Override
            protected void onSuccees(BaseEntity<CashWithdrawalBean> t) throws Exception {
                if (t.isSuccess()) {
                    cashWithdrawalBeanLiveData.setValue(t.getData());
                } else {
                    errData.setValue(t.getMsg());
                }

            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                if (isNetWorkError) {
                    errData.setValue("网络问题！");
                } else {
                    errData.setValue(e.getMessage());
                }
            }
        });

    }
}
