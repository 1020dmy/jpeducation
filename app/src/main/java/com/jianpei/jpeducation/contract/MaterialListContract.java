package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.MaterialDataBean;
import com.jianpei.jpeducation.bean.UserInfoBean;
import com.jianpei.jpeducation.bean.homedata.MaterialInfoBean;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/9
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface MaterialListContract {

    interface Repository {
        Observable<BaseEntity<MaterialDataBean>> materialData(String cat_id, int pageIndex, int pageSize);

        Observable<BaseEntity<ArrayList<MaterialInfoBean>>> subMaterialData(String cat_id, String class_id);

    }


    interface Model {
        void materialData(String cat_id, int pageIndex, int pageSize);

        void subMaterialData(String cat_id, String class_id);

    }
}
