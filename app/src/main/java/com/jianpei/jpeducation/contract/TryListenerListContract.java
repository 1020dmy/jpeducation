package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.homedata.GroupInfoBean;
import com.jianpei.jpeducation.bean.school.TopicDataBean;

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
public interface TryListenerListContract {


    interface Repository {


        Observable<BaseEntity<List<GroupInfoBean>>> groupData(String type, String cat_id);

    }


    interface Model {


        void groupData(String type, String cat_id);


    }
}
