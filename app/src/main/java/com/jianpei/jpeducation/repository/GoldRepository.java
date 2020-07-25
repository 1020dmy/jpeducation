package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.gold.CashWithdrawalBean;
import com.jianpei.jpeducation.bean.gold.VirtualCurrencyListBean;
import com.jianpei.jpeducation.bean.gold.WithdrawalDataBean;
import com.jianpei.jpeducation.bean.json.CashWithdrawalJson;
import com.jianpei.jpeducation.bean.json.IntegralDataJson;
import com.jianpei.jpeducation.contract.GoldContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/25
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class GoldRepository extends BaseRepository implements GoldContract.Repository {

    /**
     * 1金币列表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseEntity<VirtualCurrencyListBean>> virtualCurrencyList(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().virtualCurrencyList(new IntegralDataJson(pageIndex, pageSize));
    }

    /**
     * 提现列表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseEntity<WithdrawalDataBean>> withdrawalData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().withdrawalData(new IntegralDataJson(pageIndex, pageSize));
    }

    /**
     * 申请提现
     *
     * @param bank_card
     * @param bank_user_name
     * @param virtual_currency
     * @return
     */
    @Override
    public Observable<BaseEntity<CashWithdrawalBean>> cashWithdrawal(String bank_card, String bank_user_name, String virtual_currency) {
        return RetrofitFactory.getInstance().API().cashWithdrawal(new CashWithdrawalJson(bank_card, bank_user_name, virtual_currency));
    }
}
