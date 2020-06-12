package com.jianpei.jpeducation.repository;

import com.jianpei.jpeducation.api.RetrofitFactory;
import com.jianpei.jpeducation.api.base.BaseEntity;
import com.jianpei.jpeducation.base.BaseRepository;
import com.jianpei.jpeducation.bean.classinfo.DirectoryProfessionBean;
import com.jianpei.jpeducation.bean.json.GroupInfoJson;
import com.jianpei.jpeducation.contract.CIDirectoryContract;

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
public class CIDirectoryRepository extends BaseRepository implements CIDirectoryContract.Repository {


    @Override
    public Observable<BaseEntity<List<DirectoryProfessionBean>>> classDirectory(String groupId) {

        return RetrofitFactory.getInstance().API().classDirectory(new GroupInfoJson(groupId));
    }
}
