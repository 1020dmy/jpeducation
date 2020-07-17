package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.school.AttentionDataBean;
import com.jianpei.jpeducation.bean.school.TopicDataBean;

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
public interface MineAttentionContract {


    interface Repository {


        Observable<BaseEntity<AttentionDataBean>> attentionData(int pageIndex, int pageSize);

    }


    interface Model {


        void attentionData(int pageIndex, int pageSize);


    }
}
