package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.TopicDataJson;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.contract.MineAttentionContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/15
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class MineAttentionRepository extends BaseRepository implements MineAttentionContract.Repository {

    @Override
    public Observable<BaseEntity<AttentionDataBean>> attentionData(int pageIndex, int pageSize) {
        return RetrofitFactory.getInstance().API().attentionData(new TopicDataJson(pageIndex, pageSize));
    }
}
