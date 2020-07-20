package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.HomeInfoJson;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;
import com.jianpei.jpeducation.contract.TikuContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TikuRepository extends BaseRepository implements TikuContract.Repository {

    @Override
    public Observable<BaseEntity<PaperHomeBean>> paperHome(String cat_id) {
        return RetrofitFactory.getInstance().API().paperHome(new HomeInfoJson(cat_id));
    }
}
