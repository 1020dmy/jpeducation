package com.jianpei.jpeducation.contract;

import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.bean.classinfo.ClassInfoBean;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.classinfo.DirectorySectionBean;
import com.jianpei.jpeducation.bean.classinfo.ViodListBean;
import com.jianpei.jpeducation.bean.mclass.ViodBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * jpeducation
 * <p>
 * Created by sjl on 2020/6/12
 * Copyright © 2020年 weibo. All rights reserved.
 * <p>
 * Describe:
 */
public interface CIDirectoryContract {

    interface Repository {

        Observable<BaseEntity<List<DirectoryProfessionBean>>> classDirectory(String groupId);

        Observable<BaseEntity<List<ViodBean>>> viodList(String class_id, String chapter_id, String type);


    }


    interface Model {
        void classDirectory(String groupId);

        void viodList(String class_id, String chapter_id);
    }
}
