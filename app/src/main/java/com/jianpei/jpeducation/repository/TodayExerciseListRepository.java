package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.json.PaperDataJson;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;
import com.jianpei.jpeducation.contract.TodayExerciseListContract;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public class TodayExerciseListRepository extends BaseRepository implements TodayExerciseListContract.Repository {

    @Override
    public Observable<BaseEntity<PaperDataBean>> paperData(int pageIndex, int pageSize, String cat_id, String class_id, String chapter_id, String paper_type) {
        return RetrofitFactory.getInstance().API().paperData(new PaperDataJson(pageIndex, pageSize, cat_id, class_id, chapter_id, paper_type));
    }
}
