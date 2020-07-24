package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.tiku.PaperDataBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/21
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface TodayExerciseListContract {

    interface Repository {
        Observable<BaseEntity<PaperDataBean>> paperData(int pageIndex, int pageSize, String cat_id, String class_id, String chapter_id, String paper_type);
    }

    interface Model {
        void paperData(int pageIndex, int pageSize, String cat_id, String class_id, String chapter_id, String paper_type);
    }
}
