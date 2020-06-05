package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.DownloadBean;
import com.jianpei.jpeducation.bean.DownloadJson;
import com.jianpei.jpeducation.bean.IntegrlPayJson;
import com.jianpei.jpeducation.contract.MaterialContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MaterialRepository implements MaterialContract.Repository {

    @Override
    public Observable<BaseEntity<DownloadBean>> getDownloadUrl(String fileId) {
        return RetrofitFactory.getInstance().API().getDownloadUrl(new DownloadJson(fileId));
    }

    @Override
    public Observable<BaseEntity<String>> integrlPay(String integrl) {
        return RetrofitFactory.getInstance().API().integrlPay(new IntegrlPayJson(3, integrl));
    }
}
