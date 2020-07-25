package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.gold.CashWithdrawalBean;
import com.jianpei.jpeducation.bean.gold.VirtualCurrencyListBean;
import com.jianpei.jpeducation.bean.gold.WithdrawalDataBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface GoldContract {


    interface Repository {

        //金币列表
        Observable<BaseEntity<VirtualCurrencyListBean>> virtualCurrencyList(int pageIndex, int pageSize);

        //提现列表
        Observable<BaseEntity<WithdrawalDataBean>> withdrawalData(int pageIndex, int pageSize);

        //申请提现
        Observable<BaseEntity<CashWithdrawalBean>> cashWithdrawal(String bank_card, String bank_user_name, String virtual_currency);

    }


    interface Model {
        void virtualCurrencyList(int pageIndex, int pageSize);

        void withdrawalData(int pageIndex, int pageSize);

        void cashWithdrawal(String bank_card, String bank_user_name, String virtual_currency);
    }
}
