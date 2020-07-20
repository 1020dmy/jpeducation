package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;
import com.jianpei.jpeducation.bean.tiku.PaperHomeBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface TikuContract {

    interface Repository {
        Observable<BaseEntity<PaperHomeBean>> paperHome(String cat_id);
    }


    interface Model {
        void paperHome(String cat_id);
    }
}
