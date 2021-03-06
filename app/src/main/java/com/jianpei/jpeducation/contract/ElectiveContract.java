package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.CommentListBean;
import com.jianpei.jpeducation.bean.elective.GroupHomeBean;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/7/20
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface ElectiveContract {

    interface Repository {
        Observable<BaseEntity<GroupHomeBean>> groupHome(String cat_id);

    }


    interface Model {

        void groupHome(String cat_id);


    }
}
