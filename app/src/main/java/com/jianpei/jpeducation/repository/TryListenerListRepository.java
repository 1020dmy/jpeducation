package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.json.GroupDataJson;
import com.jianpei.jpeducation.contract.TryListenerListContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/8/4
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TryListenerListRepository extends BaseRepository implements TryListenerListContract.Repository {

    @Override
    public Observable<BaseEntity<List<GroupInfoBean>>> groupData(String type, String cat_id) {
        return RetrofitFactory.getInstance().API().groupData(new GroupDataJson(type, cat_id));
    }
}
